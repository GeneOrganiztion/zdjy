package service.impl;

import com.abc.spring.FileUpload;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Mapper.ImageMapper;
import po.Image;
import service.ImageService;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    @Autowired
    private ImageMapper imageMapper;

    public boolean addImage(Image image) throws Exception {
        boolean flag = false;
        try {
            image.setAssessId(0);
            imageMapper.insertSelective(image);
            flag = true;
        } catch (Exception e) {
            logger.info("addImage=" + e);
        }
        return flag;
    }

    public List<Image> selectbyProduct(Image image) throws Exception {
        return imageMapper.select(image);
    }

    public boolean deleteImage(Image image) throws Exception {
        boolean flag = false;
        try {
            imageMapper.delete(image);
            flag = true;
        } catch (Exception e) {
            logger.info("deleteImage=" + e);
        }
        return flag;
    }

    public Image selectbyId(Image image) throws Exception {
        return (Image) imageMapper.selectOne(image);
    }

    public List<Image> saveImages(String[] pathArr, int assessId) throws Exception {
        List<Image> list = new ArrayList<Image>();
        for (String path : pathArr) {
            Image image = new Image();
            image.setUrl(path);
            image.setAssessId(assessId);
            saveImage(image);
            list.add(image);
        }
        return list;
    }

    public List<Image> ImagebyProductId(Image image) throws Exception {

        return imageMapper.select(image);
    }

    public Image saveImage(Image image) throws Exception {
        imageMapper.saveImage(image);
        return image;
    }

    public void delImageById(int imageId) throws Exception {
        Image image = new Image();
        image.setImageId(imageId);
        Image imageDb = selectbyId(image);
        //先删除图片文件，在删除记录
        FileUpload.deleteObject(imageDb.getUrl());
        imageMapper.delete(image);
    }

    public List<Image> findImageByAssessId(int assessId) throws Exception {
        Image image = new Image();
        image.setAssessId(assessId);
        return imageMapper.select(image);
    }

}
