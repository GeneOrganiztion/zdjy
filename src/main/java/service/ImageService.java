package service;

import java.util.List;

import po.Assess;
import po.Image;

public interface ImageService<T> {
	
	public boolean addImage(Image image) throws Exception;
	
	public List<T> selectbyProduct(Image image) throws Exception;
	
	public boolean deleteImage(Image image)throws Exception;
	
	
	public List<T> ImagebyProductId(Image image) throws Exception;
	
	public T selectbyId(Image image)throws Exception;

	List<Image> saveImages(String[] pathArr, int assessId) throws Exception;

	Image saveImage(Image image) throws Exception;

    void delImageById(int imageId) throws Exception;

    List<Image> findImageByAssessId(int assessId) throws Exception;
	
}
