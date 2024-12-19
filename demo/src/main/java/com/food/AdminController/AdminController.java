package com.food.AdminController;

import com.food.AdminEntity.*;
import com.food.AdminRepository.*;
import com.food.AdminSevice.ManageAreaService;
import com.food.AdminSevice.ManageCategoryService;
import com.food.AdminSevice.ManageCityService;
import com.food.AdminSevice.ManageSubcategoryService;
import com.food.AdminEntity.ManageComplaint;
import com.food.RestaurantEntity.ManageOffers;
import com.food.RestaurantEntity.Restaurant;
import com.food.RestaurantRepository.ManageOffersRepository;
import com.food.RestaurantRepository.RestaurantRepository;
import com.food.AdminSevice.ManageComplaintService;
import com.food.RestaurantService.ManageOffersService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private repository repo;

    @Autowired
    private ManageCityService manageCityService;

    @Autowired
    private ManageCityRepository manageCityRepository;

    @Autowired
    private ManageAreaService manageAreaService;

    @Autowired
    private ManageAreaRepository manageAreaRepository;

    @Autowired
    private ManageCategoryService manageCategoryService;

    @Autowired
    private ManageSubcategoryService manageSubcategoryService;


    @Autowired
    private ManageOffersService manageOffersService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ManageComplaintService manageComplaintService;





    // this is for LOGIN
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/Logout")
    public String logOut() {
        return "redirect:/admin/login";
    }

    @PostMapping("/login")
    public String loginUser(Admin admin, Model model) {
        Admin existingUser = repo.findByUsername(admin.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(admin.getPassword())) {
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }




    // This is for MANAGE CITY API


    @GetMapping("/citys")
    public String listsCitys(Model model){


      return  findPaginated(1,model);

    }

    // PAGINATION.

    @GetMapping("/citys/page/{pageNo}")
    public String findPaginated( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageCity> page = manageCityService.findPaginated(pageNo,pageSize);
        List<ManageCity> listsCity = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsCity", listsCity);
        return "citys";


    }


// PDF GENERATor

    @GetMapping("/citys/generatePDF")
    public void generatePDF1(HttpServletResponse response) throws IOException {
        List<ManageCity> city = manageCityRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=city.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);



        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("City Details:");
            contentStream.endText();

            float tableWidth = 500;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 2;

            String[] headers = {"City Name", "City Decription"};

            drawTable1(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, city);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable1(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<ManageCity> city) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 3, tableWidth / 3, tableWidth / 3};

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
        for (ManageCity citys : city) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y);
            contentStream.showText(citys.getCityName());
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(citys.getCityDiscrption()));

            contentStream.endText();
            y -= rowHeight;
        }
    }




    @GetMapping("/citys/addnewcity")
    public String createCityForm(Model model){

        ManageCity city= new ManageCity();
        model.addAttribute("city","city");
        return "Add_City";
    }



    @PostMapping("/citys")
    public String saveCity(@ModelAttribute("city") ManageCity city){

    manageCityService.saveCity(city);


    return "redirect:/admin/citys";

    }

    @GetMapping("/citys/edit/{No}")
    public String editCityForm(@PathVariable Long No, Model model){
        model.addAttribute("city", manageCityService.getCityByNo(No));
        return "edit_city";

    }


    @PostMapping("/citys/{No}")
    public String updateCity(@PathVariable Long No, @ModelAttribute("city") ManageCity city, Model model){

        ManageCity existingCity = manageCityService.getCityByNo(No);
          existingCity.setNo(No);
        existingCity.setCityName(city.getCityName());
        existingCity.setCityDiscrption(city.getCityDiscrption());


        manageCityService.updateCity(existingCity);
        return "redirect:/admin/citys";


    }


    @GetMapping("/citys/{No}")
    public String DeleteCity(@PathVariable Long No){
        manageCityService.deleteCityByNo(No);
        return "redirect:/admin/citys";
    }


                        // MANAGE AREA API




    @GetMapping("/Areas")
    public String listsAreas(Model model){

        model.addAttribute("area", manageAreaService.getAllArea());
        return  findPaginated1(1,model);

    }

    // PAGINATION.

    @GetMapping("Areas/page/{pageNo}")
    public String findPaginated1( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageArea> page = manageAreaService.findPaginated(pageNo,pageSize);
        List<ManageArea> listsArea = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsArea", listsArea);
        return "Areas";


    }


    // PDF GENERATor

    @GetMapping("/Areas/generatePDF")
    public void generatePDF(HttpServletResponse response) throws IOException {
        List<ManageArea> area = manageAreaRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=area.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);






        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Area Details:");
            contentStream.endText();

            float tableWidth = 500;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 2;

            String[] headers = {"Description", "Price", "Discount"};

            drawTable(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, area);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<ManageArea> area) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 3, tableWidth / 3, tableWidth / 3};

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
        for (ManageArea areas : area) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y);
            contentStream.showText(areas.getAreaName());
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(areas.getAreaDiscrption()));
            contentStream.newLineAtOffset(columnWidths[1], 0);
            contentStream.showText(String.valueOf(areas.getCity()));
            contentStream.endText();
            y -= rowHeight;
        }
    }




    @GetMapping("/Areas/addnewArea")
    public String createAreaForm(Model model){

//        ManageArea Area= new ManageArea();
        model.addAttribute("Area", new ManageArea());
        model.addAttribute("city", manageCityService.getAllCity());
        return "Add_Area";
    }


    @PostMapping("/Areas")
    public String saveArea(@ModelAttribute("Area") ManageArea Area){

        manageAreaService.saveArea(Area);
        return "redirect:/admin/Areas";

    }


    @GetMapping("/Areas/edit/{No}")
    public String editAreaForm(@PathVariable Long No, Model model){
        model.addAttribute("Area", manageAreaService.getAreaByNo(No)    );
        return "edit_Area";

    }


    @PostMapping("/Areas/{No}")
    public String updateArea(@PathVariable Long No, @ModelAttribute("Area") ManageArea Area, Model model){

        ManageArea existingArea = manageAreaService.getAreaByNo(No);
        existingArea.setNo(No);
        existingArea.setCity(Area.getCity());
        existingArea.setAreaName(Area.getAreaName());
        existingArea.setAreaDiscrption(Area.getAreaDiscrption());

        manageAreaService.updateArea(existingArea);
        return "redirect:/admin/Areas";


    }

    @GetMapping("/Areas/{No}")
    public String DeleteArea(@PathVariable Long No){
        manageAreaService.deleteAreaByNo(No);
        return "redirect:/admin/Areas";
    }



    // MANAGE CATEGORIES API


    @GetMapping("/Categories")
    public String listsCategories(Model model){
        return  findPaginated2(1,model);

    }

    // PAGINATION.

    @GetMapping("Categories/page/{pageNo}")
    public String findPaginated2( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageCateory> page = manageCategoryService.findPaginated(pageNo,pageSize);
        List<ManageCateory> listsCategory = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsCategory", listsCategory);
        return "Categories";


    }

    @Autowired
    private ManageCategoryRepository manageCategoryRepository;

    // PDF GENERATor

    @GetMapping("/Categories/generatePDF")
    public void generatePDF2(HttpServletResponse response) throws IOException {
        List<ManageCateory> category = manageCategoryRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=category.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);






        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Category Details:");
            contentStream.endText();

            float tableWidth = 500;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 2;

            String[] headers = {"category Name", "category Decription"};

            drawTable2(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, category);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable2(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<ManageCateory> category) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 3, tableWidth / 3, tableWidth / 3};

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
        for (ManageCateory categorys : category) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y);
            contentStream.showText(categorys.getCategoryName());
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(categorys.getCategoryDiscription()));

            contentStream.endText();
            y -= rowHeight;
        }
    }







    @GetMapping("/Categories/addnewCategory")
    public String createCategoryForm(Model model){

        ManageCateory Category= new ManageCateory();
        model.addAttribute("Category","Category");
        return "Add_Category";
    }


    @PostMapping("/Categories")
    public String saveCategory(@ModelAttribute("Category") ManageCateory Category){

        manageCategoryService.saveCategory(Category);
        return "redirect:/admin/Categories";

    }



    @GetMapping("/Categories/edit/{No}")
    public String editCategoryForm(@PathVariable Long No, Model model){
        model.addAttribute("Category", manageCategoryService.getCategoryByNo(No)   );
        return "edit_Category";

    }


    @PostMapping("/Categories/{No}")
    public String updateCategory(@PathVariable Long No, @ModelAttribute("Category") ManageCateory Category, Model model){

        ManageCateory existingCategory = manageCategoryService.getCategoryByNo(No);
        existingCategory.setNo(No);
        existingCategory.setCategoryName(Category.getCategoryName());
        existingCategory.setCategoryDiscription(Category.getCategoryDiscription());

        manageCategoryService.updateCategory(existingCategory);
        return "redirect:/admin/Categories";


    }

    @GetMapping("/Categories/{No}")
    public String DeleteCategory(@PathVariable Long No){
        manageCategoryService.deleteCategoryByNo(No);
        return "redirect:/admin/Categories";
    }


// SUB CATEGORY API

    @GetMapping("/Subcategories")
    public String listsSubcategories(Model model){

        model.addAttribute("subcategory", manageSubcategoryService.getAllSubcategory());
        return findPaginated3(1,model);

    }

    // PAGINATION.

    @GetMapping("Subcategories/page/{pageNo}")
    public String findPaginated3( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageSubcategory> page = manageSubcategoryService.findPaginated(pageNo,pageSize);
        List<ManageSubcategory> listsSubCategory = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsSubCategory", listsSubCategory);
        return "SubCategories";


    }


    // PDF GENERATor

    @Autowired
    private ManageSubcategoryRepository manageSubcategoryRepository;

    @GetMapping("/Subcategories/generatePDF")
    public void generatePDF3(HttpServletResponse response) throws IOException {
        List<ManageSubcategory> Subcategory = manageSubcategoryRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Subcategory.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);






        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Category Details:");
            contentStream.endText();

            float tableWidth = 500;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 2;

            String[] headers = {"Category Name","Subcategory Name", "Subcategory Decription"};

            drawTable3(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, Subcategory);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable3(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<ManageSubcategory> Subcategory) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 3, tableWidth / 3, tableWidth / 3};

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
        for (ManageSubcategory subcategory : Subcategory) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y);
            contentStream.showText(String.valueOf(subcategory.getCategoryName()));
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(subcategory.getSubcategoryName());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(subcategory.getSubcategoryDiscription()));

            contentStream.endText();
            y -= rowHeight;
        }
    }



    @GetMapping("/Subcategories/addnewSubcategory")
    public String createSubcategoryForm(Model model){

//        ManageSubcategory Subcategory= new ManageSubcategory();
        model.addAttribute("Subcategory", new ManageSubcategory());
        model.addAttribute("Category",  manageCategoryService.getAllCategory());
        return "Add_Subcategory";
    }

    @PostMapping("/Subcategories")
    public String saveSubcategory(@ModelAttribute("Subcategory") ManageSubcategory Subcategory){

        manageSubcategoryService.saveSubcategory(Subcategory);
        return "redirect:/admin/Subcategories";

    }

    @GetMapping("/Subcategories/edit/{No}")
    public String editSubcategoryForm(@PathVariable Long No, Model model){
        model.addAttribute("Subcategory", manageSubcategoryService.getSubcategoryByNo(No)   );
        return "edit_Subcategory";

    }


    @PostMapping("/Subcategories/{No}")
    public String updateSubcategory(@PathVariable Long No, @ModelAttribute("Subcategory") ManageSubcategory Subcategory, Model model){

        ManageSubcategory existingSubcategory = manageSubcategoryService.getSubcategoryByNo(No);
        existingSubcategory.setNo(No);
        existingSubcategory.setCategoryName(Subcategory.getCategoryName());
        existingSubcategory.setSubcategoryName(Subcategory.getSubcategoryName());
        existingSubcategory.setSubcategoryDiscription(Subcategory.getSubcategoryDiscription());

        manageSubcategoryService.updateSubcategory(existingSubcategory);
        return "redirect:/admin/Subcategories";


    }

    @GetMapping("/Subcategories/{No}")
    public String DeleteSubcategory(@PathVariable Long No){
        manageSubcategoryService.deleteSubcategoryByNo(No);
        return "redirect:/admin/Subcategories";
    }

// MANAGE OFFERS API

    @GetMapping("/ManageOffers")
    public String listsOfAllOffers(Model model){
        model.addAttribute("Offers", manageOffersService.getAllOffers());
        return "ShowAdminOffer";

    }

    // PDF GENERATor

    @Autowired
    private ManageOffersRepository manageOffersRepository;


    @GetMapping("/ManageOffers/generatePDF")
    public void generatePDF5(HttpServletResponse response) throws IOException {
        List<ManageOffers> offer = manageOffersRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename= Offer.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);



        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Restaurant Details:");
            contentStream.endText();

            float tableWidth = 400;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 2;

            String[] headers = {"Category"," Subcategory", "OfferName ","StartDate", "EndDate","Discount"};

            drawTable5(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, offer);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable5(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<ManageOffers> offer) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 5, tableWidth / 5, tableWidth / 5, tableWidth / 5, tableWidth / 5,tableWidth / 5};

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
        for (ManageOffers offers : offer) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();

            contentStream.newLineAtOffset(100, y);
            contentStream.showText(offers.getCategoryName());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(offers.getSubcategoryName());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(offers.getOffersName()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(offers.getStartDate()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(offers.getEndDate()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(offers.getDiscount()));

            contentStream.endText();
            y -= rowHeight;
        }
    }










// MANAGE SEE ALL RESTAURANT API


    @GetMapping("/AllRestaurant")
    public String listsOfAllres(Model model){
        model.addAttribute("res",restaurantRepository.findAll());

        return "ShowAllRestaurant";

    }

    // PDF GENERATor


    @GetMapping("/AllRestaurant/generatePDF")
    public void generatePDF4(HttpServletResponse response) throws IOException {
        List<Restaurant> restaurant = restaurantRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename= Restaurant.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);






        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Restaurant Details:");
            contentStream.endText();

            float tableWidth = 500;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 1;

            String[] headers = {"Restaurant Name"," Email", "Contact ","Address", "City"};

            drawTable4(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, restaurant);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable4(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<Restaurant> restaurant) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 5, tableWidth / 5, tableWidth / 5, tableWidth / 5, tableWidth / 5};

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
        for (Restaurant restaurants : restaurant) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();

            contentStream.newLineAtOffset(100, y);
            contentStream.showText(restaurants.getRestaurantName());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(restaurants.getEmail());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(restaurants.getContact()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(restaurants.getAddress()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(restaurants.getCity()));

            contentStream.endText();
            y -= rowHeight;
        }
    }




    // MANAGE ADMIN COMPLAINT API

    @GetMapping("/ManageAdminComplaints")
    public String listsComplaints( Model model ){
        model.addAttribute("comp", manageComplaintService.getAllComplaints());
        return findPaginated5(1,model);

    }


    // PDF GENERATor

    @Autowired
    private ManageComplaintRepository manageComplaintRepository;



    @GetMapping("/ManageAdminComplaints/generatePDF")
    public void generatePDF10(HttpServletResponse response) throws IOException {
        List<ManageComplaint> complaint = manageComplaintRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename= Complaint.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);






        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Complaint Details:");
            contentStream.endText();

            float tableWidth = 500;
            float yStart = 680;
            float bottomMargin = 50;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 1;

            String[] headers = {"Subject "," Description", "ComplaintDate","reply ","ReplyDate"};

            drawTable10(contentStream, yStart, tableWidth, yPosition, rowHeight, cellMargin, headers, complaint);
        }

        document.save(response.getOutputStream());
        document.close();
    }
    private void drawTable10(PDPageContentStream contentStream, float yStart, float tableWidth, float yPosition, float rowHeight, float cellMargin, String[] headers, List<ManageComplaint> complaint) throws IOException {
        float y = yStart;
        float nextY;
        int numberOfColumns = headers.length;
        float[] columnWidths = {tableWidth / 5, tableWidth / 5, tableWidth / 5, tableWidth / 5, tableWidth / 5};

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
        for (ManageComplaint compaints : complaint) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();

            contentStream.newLineAtOffset(100, y);
            contentStream.showText(compaints.getSubject());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(compaints.getDiscription());

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(compaints.getComplaintDate()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(compaints.getReply()));

            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(compaints.getReplyDate()));

            contentStream.endText();
            y -= rowHeight;
        }
    }


    // PAGINATION.

    @GetMapping("ManageAdminComplaints/page/{pageNo}")
    public String findPaginated5( @PathVariable (value = "pageNo") int pageNo, Model model  ){

        int pageSize=3;
        Page<ManageComplaint> page = manageComplaintService.findPaginated(pageNo,pageSize);
        List<ManageComplaint> listsComplaint = page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listsComplaint", listsComplaint);
        return "ManageAdminComplaint";


    }








    @GetMapping("/ManageAdminComplaints/addnewComplaints")
    public String createComplaintForm(Model model){


        model.addAttribute("Complaints",new ManageComplaint());
        model.addAttribute("res", restaurantRepository.findAll());
        return "Add_Complaints";
    }

    @PostMapping("/ManageAdminComplaints")
    public String saveComplaint(@ModelAttribute("Complaints") ManageComplaint Complaints){

        manageComplaintService.saveComplaints(Complaints);
        return "redirect:/admin/ManageAdminComplaints";

    }



}
