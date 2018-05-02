package controler.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import po.Assess;
import po.Image;
import po.Product;
import po.ResModel;
import po.ResponseMessage;
import service.AssessService;
import service.ImageService;
import service.ProductService;
import utils.ST;
import com.abc.spring.FileUpload;
import com.github.pagehelper.PageInfo;
import controler.base.BaseController;

@Controller
@RequestMapping(value = "/product")
public class ProductControler extends BaseController {

    private static final Logger logger = LoggerFactory
        .getLogger(ProductControler.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private AssessService assessService;
    @Autowired
    private ImageService imageService;
    private String PRODUCTSELECT_PAGE = "product/productall";
    private String PRODUCT_PAGE = "product/product";

    @RequestMapping(value = "/productAddPage")
    public ModelAndView productPage(HttpServletRequest request,
        HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(PRODUCT_PAGE);
        return mv;
    }

    @RequestMapping(value = "/productPage")
    public ModelAndView productSeletPage(HttpServletRequest request,
        HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(PRODUCTSELECT_PAGE);
        return mv;
    }


    /**
     * 添加商品
     *
     * @param response -1代表插入商品失败 >0代表插入成功(返回添加商品的id)
     */
    @RequestMapping(value = "/insertProduct")
    @ResponseBody
    public int insertProduct(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        int proid = -1;
        String classifyid = getParam("classify");
        String name = getParam("name");
        String head = getParam("head");
        String price = getParam("price");
        String geneNum = getParam("genenum");
        String comment = getParam("comment");
        String sum = getParam("sum");
        String rateprice = getParam("rateprice");
        String isonline = getParam("isonline");
        String ishot = getParam("ishot");
        try {
            Product product = new Product();
            product.setClassifyId(Integer.valueOf(classifyid));
            product.setProName(name);
            product.setProArea(geneNum);
            product.setIshotsell(Integer.valueOf(ishot));
            product.setProHead(head);
            product.setProRemark(comment);
            product.setSelNumber(0);
            product.setProductPrice(Integer.valueOf(price));
            product.setProRateprice(Integer.valueOf(rateprice));
            product.setProSum(Integer.valueOf(sum));
            if (Integer.valueOf(isonline) == 1) {
                product.setProOnline(true);
            } else {
                product.setProOnline(false);
            }
            proid = productService.saveProduct(product);
        } catch (Exception e) {
            logger.info("insertProduct" + e);
        }

        System.out.println("proid=" + proid);
        return proid;

    }

    /**
     * 添加商品图片
     *
     * @param response 最多添加5张图
     */
    @RequestMapping(value = "/UploadImage")
    @ResponseBody
    public ResponseMessage UploadImage(HttpServletRequest request,
        @RequestParam("file") MultipartFile file) throws Exception {
        ResponseMessage msg = new ResponseMessage();
        String filepathurl = null;
        String pro_id = getParam("pro_id");
        System.out.println("pro_id=" + pro_id);
        try {
            Image image = new Image();
            image.setProId(Integer.valueOf(pro_id));
            List<Image> listimage = imageService.selectbyProduct(image);
            if (null == pro_id || "".equals(pro_id)) {
                msg.setMessage("error");
            } else {
                if (listimage.size() < 5) {
                    String filepath = FileUpload.uploadFile(file, request);
                    filepathurl = filepath;
                    image.setUrl(filepath);
                    imageService.addImage(image);
                    msg.setMessage(filepathurl);
                } else {
                    msg.setMessage("tomore");
                }
            }
        } catch (Exception e) {
            msg.setMessage("error");
            logger.info("UploadImage" + e);
            e.printStackTrace();
            return null;
        }
        return msg;

    }


    /**
     * 根据产品ID查询产品展示图片
     */
    @RequestMapping(value = "/SelectImage")
    @ResponseBody
    public boolean SelectImage(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String pro_id = getParam("id");
        boolean flag = false;
        try {
            Image image = new Image();
            image.setProId(Integer.valueOf(pro_id));
            List<Image> listImage = imageService.ImagebyProductId(image);
            if (listImage.size() == 0) {

            } else {
                flag = true;
            }

        } catch (Exception e) {
            logger.info("SelectImage" + e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据产品ID添加产品详情
     */
    @RequestMapping(value = "/addProductContent")
    @ResponseBody
    public Boolean addProductContent(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String pro_id = getParam("ProductId");
        String proDetail = getParam("productDetail");
        Product product = new Product();
        boolean flag = false;
        try {
            product.setId(Integer.valueOf(pro_id));
            product.setProDetail(proDetail);
            flag = productService.updateProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("addProductContent" + e);
        }
        return flag;
    }

    /**
     * 根据产品ID删除商品展示图
     */
    @RequestMapping(value = "/DeleteShowImage")
    @ResponseBody
    public ResponseMessage DeleteShowImage(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String url = getParam("filename");
        ResponseMessage msg = new ResponseMessage();
        try {
            String filename = ST.getFileName(url);
            if (FileUpload.deleteObject(filename)) {
                Image image = new Image();
                image.setUrl(url);
                if (imageService.deleteImage(image)) {
                    msg.setMessage("success");
                } else {
                    msg.setMessage("error");
                }
            } else {
                msg.setMessage("error");
            }
        } catch (Exception e) {
            logger.info("DeleteShowImage" + e);
            e.printStackTrace();
            msg.setMessage("error");
        }
        return msg;
    }

    /**
     * 上传商品详情图片
     */

    @RequestMapping(value = "/UploadDetailImage")
    @ResponseBody
    public ResponseMessage UploaddetailImage(HttpServletRequest request,
        @RequestParam("file") MultipartFile file) throws Exception {
        String name = getParam("name");
        ResponseMessage msg = new ResponseMessage();
        try {
            String filepathurl = null;
            String filepath = FileUpload.uploadFile(file, request);
            filepathurl = filepath;
            msg.setMessage(filepathurl);
        } catch (Exception e) {
            logger.info("UploaddetailImage" + e);
            msg.setMessage("error");
        }
        return msg;
    }

    /**
     * 删除商品详情图片
     */
    @RequestMapping(value = "/DeleteImage")
    @ResponseBody
    public ResponseMessage DeleteImage(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String url = getParam("filename");
        ResponseMessage msg = new ResponseMessage();
        try {
            String filename = ST.getFileName(url);
            if (FileUpload.deleteObject(filename)) {
                msg.setMessage("success");
            } else {
                msg.setMessage("error");
            }
        } catch (Exception e) {
            logger.info("DeleteImage" + e);
            e.printStackTrace();
            msg.setMessage("error");
        }
        return msg;
    }

    /**
     * 后台查询商品
     */
    @RequestMapping(value = "/selectProduct")
    @ResponseBody
    public PageInfo selectAdminByParams(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String sidx = getParam("sidx");// 排序字段;
        String sord = getParam("sord");// 升序降序;

        PageInfo pageInfo = new PageInfo();
        try {
            int oneRecord = Integer.valueOf(getParam("rows"));// 一页几行
            int pageNo = Integer.valueOf(getParam("page"));// 第几页
            String productName = getParam("productName");
            if ("".equals(productName) || null == productName) {

            } else {
                System.out.println("proName="
                    + new String(new String(productName
                    .getBytes("iso-8859-1"), "UTF-8")));
            }
            String productID = getParam("productID");
            String productOnline = getParam("productOnline");
            String oneclassify_id = getParam("oneclassify");
            // String classify_id = getParam("classify");
            Map<String, Object> map = new HashMap<String, Object>();
            List<Integer> list = null;
            // String clslist="";
            if (null == oneclassify_id) {
                map.put("classifylist", null);
            } else {
                map.put("classifylist", ST.StringToList(oneclassify_id));
            }

            // if(!ST.isNull(clslist)){
            // list=ST.StringToList(clslist);
            // System.out.println("list="+list);
            // map.put("classifylist",list);
            // }
            Object IsOnline = true;
            if ("true".equals(productOnline)) {
                IsOnline = true;
            } else if ("false".equals(productOnline)) {
                IsOnline = false;
            } else {
                IsOnline = null;
            }

            map.put("productOnline", IsOnline);
            String beginTime = getParam("beginTime");
            String endTime = getParam("endTime");
            map.put("productName", productName);
            map.put("productID", productID);
            map.put("sidx", sidx);// 排序字段
            map.put("sord", sord);// 升序降序
            map.put("rowCount", oneRecord);// 一页几行
            map.put("pageNo", pageNo);

            if (!ST.isNull(beginTime)) {
                map.put("beginTime", beginTime + " 00:00:00");
            }
            if (!ST.isNull(endTime)) {
                map.put("endTime", endTime + " 59:59:59");
            }
            pageInfo = (PageInfo) productService.selectProductListByParams(map);
        } catch (Exception e) {
            logger.error("selectProduct error:" + e);
        }
        return pageInfo;
    }

    /**
     * 查询商品图片
     */
    @RequestMapping(value = "/selectImageProduct")
    @ResponseBody
    public ResModel selectImageProduct(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        ResModel resModel = new ResModel();
        String pro_id = getParam("ProductId");
        Image image = new Image();
        List<Image> imagelist = null;
        try {
            image.setProId(Integer.valueOf(pro_id));
            imagelist = imageService.ImagebyProductId(image);
        } catch (Exception e) {
            logger.info("selectImageProduct" + e);
            resModel.setSuccess(false);
            return resModel;
        }
        resModel.setSuccess(true);
        resModel.setData(imagelist);
        return resModel;
    }

    /**
     * 后台修改商品
     */
    @RequestMapping(value = "/updateProduct")
    @ResponseBody
    public boolean updateProduct(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        boolean falg = false;
        String pro_id = getParam("id");
        String classifyid = getParam("classify");
        String name = getParam("name");
        String head = getParam("head");
        String price = getParam("price");
        String sum = getParam("sum");
        String proArea = getParam("area");
        String rateprice = getParam("rateprice");
        String isonline = getParam("isonline");
        String ishotPro = getParam("ishot");
        try {
            Product product = new Product();
            product.setId(Integer.valueOf(pro_id));
            product.setClassifyId(Integer.valueOf(classifyid));
            product.setProName(name);
            product.setProArea(proArea);
            product.setProHead(head);
            product.setProductPrice(Integer.valueOf(price));
            product.setProRateprice(Integer.valueOf(rateprice));
            product.setProSum(Integer.valueOf(sum));
            product.setIshotsell(Integer.valueOf(ishotPro));
            if (Integer.valueOf(isonline) == 1) {
                product.setProOnline(true);
            } else {
                product.setProOnline(false);
            }
            falg = productService.updateProduct(product);
        } catch (Exception e) {
            logger.info("updateProduct" + e);
        }
        return falg;

    }

    /**
     * 后台删除商品
     */
    @RequestMapping(value = "/deleteproduct")
    @ResponseBody
    public boolean delete(HttpServletRequest request,
        HttpServletResponse response) {
        String productIds = getParam("ProductId");
        try {
            List<Integer> list = ST.StringToList(productIds);
            productService.deleteProductIds(list);
        } catch (Exception e) {
            logger.error("deleteProduct error:" + e);
            return false;
        }
        return true;
    }

    /**
     * 后台查询商品
     */
    @RequestMapping(value = "/selectOneProduct")
    @ResponseBody
    public Product selectOneProduct(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String pro_id = getParam("proId");
        Product ResultProduct = new Product();
        try {
            Product product = new Product();
            product.setId(Integer.valueOf(pro_id));
            ResultProduct = (Product) productService.selectOne(product);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("selectOneProduct" + e);
        }
        return ResultProduct;
    }

    /**
     * 根据产品的参数查询符合条件的产品
     */
    @RequestMapping(value = "/selectProductByParams")
    @ResponseBody
    public List<Product> selectProductByParams(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        String proName = getParam("proName");
        List<Product> ResultProduct = new ArrayList<Product>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productName", proName);
            ResultProduct = productService.findProductByParms(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("selectProductByParams" + e);
        }
        return ResultProduct;
    }

    @RequestMapping(value = "/phoneProAll")
    @ResponseBody
    public List<Product> phoneProAll(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        List<Product> listproduct = new ArrayList<Product>();
        String clsId = getParam("clsId");
        try {
            Product product = new Product();
            product.setClassifyId(ST.parseInt(clsId));
            listproduct = productService.selectbyClassify(product);
        } catch (Exception e) {
            logger.info("phoneproall error:" + e);
        }
        return listproduct;
    }

    /**
     * 热销商品接口
     */
    @RequestMapping(value = "/phoneHotPro")
    @ResponseBody
    public List<Product> phoneHotPro(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        List<Product> listproduct = new ArrayList<Product>();
        try {
            Product product = new Product();
            product.setIshotsell(1);
            listproduct = productService.selectbyClassify(product);
        } catch (Exception e) {
            logger.info("phoneHotPro error:" + e);
        }
        return listproduct;
    }

    /**
     * 根据产品ID查询产品详情和一个商品评价
     */
    @RequestMapping(value = "/phoneSelectProAndOneAssess")
    @ResponseBody
    public Product phoneSelectProAndOneAssess(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        Product pro = new Product();
        String proId = getParam("proId");
        if (ST.isNull(proId)) {
            return pro;
        }
        try {
            Product product = new Product();
            product.setId(Integer.valueOf(proId));
            pro = productService.phoneSelectProAndOneAssess(product);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("proId", pro.getId());
            Integer totalCount = assessService.totalAssessByProId(pro.getId());
            Assess assess = assessService.selectOneAssessByProId(map);
            pro.setAssess(assess);
            pro.setAssessTotal(totalCount);
        } catch (Exception e) {
            logger.info("phoneHotPro error:" + e);
        }
        return pro;
    }
}
