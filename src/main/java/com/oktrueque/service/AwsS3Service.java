package com.oktrueque.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

@Service
public class AwsS3Service {

    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.secretKey}")
    private String secretKey;
    @Value("${aws.s3.region}")
    private String region;
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Value("${dir.images}")
    private String directoryPath;

    private ResponseEntity<URL> upload(String fileName, String uploadKey) {
        AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).withRegion(region).build();
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(fileName);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, file);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            s3client.putObject(putObjectRequest);
            return new ResponseEntity<>(s3client.getUrl(bucket, uploadKey), HttpStatus.OK);
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return ResponseEntity.noContent().build();

        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    public String uploadFileToS3(MultipartFile file, String fileNamePattern, Long id, String suffix, String previousPhoto) {
        String extension = this.getFileExtension(file.getOriginalFilename()).toLowerCase();
        if(extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg")){
            try {
                Random random = new Random();
                String fileName = random.nextInt(10000) + file.getOriginalFilename().replace(" ", "_");
                BufferedImage croppedImage = cropImageSquare(file.getBytes());
                String filePath = directoryPath + fileName;
                File outputFile = new File(filePath);
                ImageIO.write(croppedImage, this.getFileExtension(fileName), outputFile);

                ResponseEntity<URL> responseEntity;
                if(suffix != null) responseEntity = this.upload(filePath, String.format(fileNamePattern, id, suffix));
                else responseEntity = this.upload(filePath, String.format(fileNamePattern, id));

                String url = responseEntity.getBody().toString();
                if (url.isEmpty()) {
                    throw new Exception("Fail to move file");
                } else {
                    outputFile.delete();
                    return url;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return previousPhoto;
            }
        }
        return previousPhoto;
    }

    private BufferedImage cropImageSquare(byte[] image) throws IOException {
        // Get a BufferedImage object from a byte array
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        // Get image dimensions
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        // The image is already a square
        if (height == width) {
            return originalImage;
        }

        // Compute the size of the square
        int squareSize = (height > width ? width : height);

        // Coordinates of the image's middle
        int xc = width / 2;
        int yc = height / 2;

        // Crop
        BufferedImage croppedImage = originalImage.getSubimage(
                xc - (squareSize / 2), // x coordinate of the upper-left corner
                yc - (squareSize / 2), // y coordinate of the upper-left corner
                squareSize,            // widht
                squareSize             // height
        );

        return croppedImage;
    }

    private String getFileExtension(String fileName){
        String extension = null;
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
}
