package com.food.ResturantController;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageComplaint;
import com.food.AdminSevice.ManageAreaService;
import com.food.AdminSevice.ManageCityService;
import com.food.RestaurantEntity.ManageOffers;
import com.food.RestaurantEntity.ManageProduct;
import com.food.RestaurantEntity.Restaurant;
import com.food.AdminSevice.ManageComplaintService;
import com.food.RestaurantRepository.ManageOffersRepository;
import com.food.RestaurantRepository.ManageProductRepository;
import com.food.RestaurantService.ManageOffersService;
import com.food.RestaurantService.ManageProductService;
import com.food.RestaurantService.RestoService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class RestaurantControlller {



    @Autowired
    private RestoService service;

    @Autowired
    private ManageOffersService manageOffersService;

  @Autowired
  private ManageProductService manageProductService;

  @Autowired
  private ManageComplaintService manageComplaintService;

  @ModelAttribute
  public  void commonData(){

  }

  @Autowired
  private ManageCityService manageCityService;

  @Autowired
  private ManageAreaService manageAreaService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("city", manageCityService.getAllCity());
        model.addAttribute("area", manageAreaService.getAllArea());
        return "register";
    }

    @PostMapping("/register")
    public String registerRestaurant(Restaurant restaurant) {
        service.saveRestaurant(restaurant);
        return "redirect:/Restaurantlogin";
    }

    @GetMapping("/Restaurantlogin")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new Restaurant());
        return "loginpage";
    }

    @PostMapping("/Restaurantlogin")
    public String login(Restaurant user, Model model) {
        Restaurant authenticatedUser = service.authenticate(user.getRestaurantName(), user.getEmail());
        if (authenticatedUser != null ) {
            return "redirect:/RestaurantDashboard";
        } else {
//            model.addAttribute("Error", "Invalid username or password");
//            return "/Restaurantlogin";

            return "redirect:/Restaurantlogin";
        }
    }

    @GetMapping ("/RestaurantDashboard")
    public String showMainPage() {
        return "RestaurantDashboard";
    }






// MANAGE PRODUCT API



    @GetMapping("/ManageProduct")
    public String listsProduct(Model model){
        return findPaginated7(1,model);

    }

    // PAGINATION.

    @GetMapping("ManageProduct/page/{pageNo}")
    public String findPaginated7( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageProduct> page = manageProductService.findPaginated(pageNo,pageSize);
        List<ManageProduct> listsProduct = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsProduct", listsProduct);
        return "ManageProduct";


    }


    // PDF GENERATor

    @Autowired
    private ManageProductRepository manageProductRepository;


    @GetMapping("/ManageProduct/generatePDF")
    public void generatePDF6(HttpServletResponse response) throws IOException {
        List<ManageProduct> product = manageProductRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename= Product.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);



        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Product Details:");
            contentStream.endText();

            float tableWidth = 500;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 2;

            String[] headers = {"Category"," Subcategory", "ProductName ","Prize"};

            drawTable5(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, product);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable5(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<ManageProduct> product
    ) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 5, tableWidth / 5, tableWidth / 5, tableWidth / 5};

        // Draw headers
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, y);
        for (String header : headers) {
            contentStream.showText(header);
            contentStream.newLineAtOffset(columnWidths[0], 0);
        }
        contentStream.endText();
        y -= rowHeight;

        // Draw rows
        for (ManageProduct products : product) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();

            contentStream.newLineAtOffset(100, y);
            contentStream.showText(products.getCategory().toString());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(products.getSubcategoryName());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(products.getProductName()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(products.getProductPrize()));



            contentStream.endText();
            y -= rowHeight;
        }
    }






    @GetMapping("/ManageProduct/addnewProduct")
    public String createProductForm(Model model){

        ManageProduct Product= new ManageProduct();
        model.addAttribute("Product","Product");
        return "Add_Product";
    }

    @PostMapping("/ManageProduct")
    public String saveProduct(@ModelAttribute("Product") ManageProduct Product){

        manageProductService.saveProduct(Product);
        return "redirect:/ManageProduct";

    }

    @GetMapping("/ManageProduct/edit/{No}")
    public String editProductForm(@PathVariable Long No, Model model){
        model.addAttribute("Product", manageProductService.getProductByNo(No)   );
        return "edit_Product";

    }


    @PostMapping("/ManageProduct/{No}")
    public String updateProduct(@PathVariable Long No, @ModelAttribute("Product") ManageProduct Product, Model model){

        ManageProduct existingProduct = manageProductService.getProductByNo(No);
        existingProduct.setNo(No);
        existingProduct.setCategory(Product.getCategory());
        existingProduct.setSubcategoryName(Product.getSubcategoryName());
        existingProduct.setProductName(Product.getProductName());
        existingProduct.setProductPrize(Product.getProductPrize());
        existingProduct.setProductDiscription(Product.getProductDiscription());


        manageProductService.updateProduct(existingProduct);
        return "redirect:/ManageProduct";


    }

    @GetMapping("/ManageProduct/{No}")
    public String DeleteProduct(@PathVariable Long No){
        manageProductService.deleteProductByNo(No);
        return "redirect:/ManageProduct";
    }





    // MANAGE OFFERS API

    @GetMapping("/ManageOffers")
    public String listsOffers(Model model){
        model.addAttribute("offers", manageOffersService.getAllOffers());
        return findPaginated8(1,model);

    }

    @GetMapping("ManageOffers/page/{pageNo}")
    public String findPaginated8( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageOffers> page = manageOffersService.findPaginated(pageNo,pageSize);
        List<ManageOffers> listsOffers = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsOffers", listsOffers);
        return "ManageOffers";


    }

    @GetMapping("/ManageOffers/addnewOffers")
    public String createOfferForm(Model model){

        model.addAttribute("Offers" , new ManageOffers());
        model.addAttribute("pro", manageProductService.getAllProduct());
        return "Add_Offers";
    }

    @PostMapping("/ManageOffers")
    public String saveOffer(@ModelAttribute("Offers") ManageOffers Offers){

        manageOffersService.saveOffers(Offers);
        return "redirect:/ManageOffers";

    }

    @GetMapping("/ManageOffers/edit/{No}")
    public String editOfferForm(@PathVariable Long No, Model model){
        model.addAttribute("Offers", manageOffersService.getOfferByNo(No)   );
        return "edit_Offers";

    }


    @PostMapping("/ManageOffers/{No}")
    public String updateOffer(@PathVariable Long No, @ModelAttribute("Offers") ManageOffers offers, Model model){

        ManageOffers existingOffers = manageOffersService.getOfferByNo(No);
        existingOffers.setNo(No);
        existingOffers.setCategoryName(offers.getCategoryName());
        existingOffers.setSubcategoryName(offers.getSubcategoryName());
        existingOffers.setOffersName(offers.getOffersName());
        existingOffers.setOffersDiscription(offers.getOffersDiscription());
        existingOffers.setStartDate(offers.getStartDate());
        existingOffers.setEndDate(offers.getEndDate());
        existingOffers.setDiscount(offers.getDiscount());



        manageOffersService.updateOffers(existingOffers);
        return "redirect:/ManageOffers";


    }

    @GetMapping("/ManageOffers/{No}")
    public String DeleteOffer(@PathVariable Long No){
        manageOffersService.deleteOffersByNo(No);
        return "redirect:/ManageOffers";
    }



    // MANAGE COMPLAINT

    @GetMapping("/ManageRestaurantComplaints")
    public String listsComplaints(Model model){
//        model.addAttribute("Complaints", manageComplaintService.getAllComplaints() );

        return findPaginated9(1,model);
//        return "ManageRestaurantComplaint";

    }

//PAGINATION

    @GetMapping("ManageRestaurantComplaints/page/{pageNo}")
    public String findPaginated9( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageComplaint> page = manageComplaintService.findPaginated(pageNo,pageSize);
        List<ManageComplaint> listsComplaint = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsComplaint", listsComplaint);
        return "ManageRestaurantComplaint";


    }



    @GetMapping("/ManageRestaurantComplaints/edit/{No}")
    public String editComplaintForm(@PathVariable Long No, Model model){
        model.addAttribute("Complaints", manageComplaintService.getComplaintByNo(No)   );
        return "Edit_Reply_Complaints";

    }


    @PostMapping("/ManageRestaurantComplaints/{No}")
    public String updateComplaint(@PathVariable Long No, @ModelAttribute("Complaints") ManageComplaint Complaints, Model model){

        ManageComplaint existingComplaint = manageComplaintService.getComplaintByNo(No);
        existingComplaint.setNo(No);
        existingComplaint.setReply(Complaints.getReply());
        existingComplaint.setReplyDate(Complaints.getReplyDate());
        existingComplaint.setStatus(Complaints.getStatus());
//        existingOffers.setOffersDiscription(offers.getOffersDiscription());
//        existingOffers.setStartDate(offers.getStartDate());
//        existingOffers.setEndDate(offers.getEndDate());
//        existingOffers.setDiscount(offers.getDiscount());


        manageComplaintService.updateComplaints(existingComplaint);
        return "redirect:/ManageRestaurantComplaints";


    }








}







