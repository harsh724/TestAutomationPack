package utilities;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class WordReportUtils {

    private static XWPFDocument document;
    private static FileOutputStream outputStream;
    private static String filePath;

    // Start doc
    public static void startDoc(String testName) {
        try {
            filePath = "reports/" + testName + ".docx";
            document = new XWPFDocument();
            File reportsDir = new File("reports");
            if (!reportsDir.exists()) reportsDir.mkdirs();
            outputStream = new FileOutputStream(filePath);
        } catch (Exception e) {
            System.err.println("Error initializing Word doc: " + e.getMessage());
        }
    }

    // Add image with caption
    public static void addScreenshot(File screenshotFile, String caption) {
        if (document == null) return;
        try {
            BufferedImage image = ImageIO.read(screenshotFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run = paragraph.createRun();
            run.setText(caption);
            run.addBreak();
            run.addPicture(bis, Document.PICTURE_TYPE_PNG, "screenshot.png",
                    Units.toEMU(500), Units.toEMU(300));
            run.addBreak();
        } catch (Exception e) {
            System.err.println("Error adding screenshot: " + e.getMessage());
        }
    }

    // Save and close doc
    public static void saveDoc() {
        if (document != null && outputStream != null) {
            try {
                document.write(outputStream);
                outputStream.close();
                document.close();
            } catch (Exception e) {
                System.err.println("Error saving Word doc: " + e.getMessage());
            }
        }
    }
}
