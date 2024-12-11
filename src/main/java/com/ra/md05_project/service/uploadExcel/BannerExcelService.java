package com.ra.md05_project.service.uploadExcel;

import com.ra.md05_project.model.constant.BannerType;
import com.ra.md05_project.model.entity.ver1.Banner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BannerExcelService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Banner> importBannersFromExcel(String filePath) throws IOException {
        List<Banner> banners = new ArrayList<>();

        // Đọc file Excel
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        // Lặp qua các dòng trong sheet
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Bỏ qua dòng tiêu đề

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Banner banner = new Banner();

            // Đọc các cột trong mỗi dòng và xử lý dữ liệu
            banner.setId((long) row.getCell(0).getNumericCellValue());
            banner.setUrl(row.getCell(1).getStringCellValue());

            // Xử lý BannerType
            String typeValue = row.getCell(2).getStringCellValue();
            try {
                banner.setType(BannerType.valueOf(typeValue));
            } catch (IllegalArgumentException e) {
                // Xử lý lỗi khi giá trị không hợp lệ trong Enum BannerType
                System.out.println("Invalid type value: " + typeValue);
                banner.setType(BannerType.IMAGE); // Gán mặc định hoặc xử lý theo yêu cầu
            }

            banner.setPosition(row.getCell(3).getStringCellValue());

            banners.add(banner);

        }

        // Lưu các banner vào cơ sở dữ liệu sau khi đã đọc xong
        saveBannersToDatabase(banners);

        workbook.close();
        return banners;
    }

    public void exportBannersToExcel(List<Banner> banners, String filePath) throws IOException {
        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Banners");

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("URL");
        headerRow.createCell(2).setCellValue("Type");
        headerRow.createCell(3).setCellValue("Position");

        // Điền dữ liệu từ Banner vào sheet
        int rowNum = 1;
        for (Banner banner : banners) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(banner.getId());
            row.createCell(1).setCellValue(banner.getUrl());
            row.createCell(2).setCellValue(banner.getType().toString());
            row.createCell(3).setCellValue(banner.getPosition());
        }

        // Tự động điều chỉnh chiều rộng cột để dễ đọc hơn
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi workbook vào file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        // Đóng workbook
        workbook.close();
    }

    public void saveBannersToDatabase(List<Banner> banners) {
        for (Banner banner : banners) {
            entityManager.persist(banner);
        }
    }
}
