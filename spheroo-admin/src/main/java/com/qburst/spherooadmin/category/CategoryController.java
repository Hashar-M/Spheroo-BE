package com.qburst.spherooadmin.category;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.qburst.spherooadmin.exception.WrongDataForActionException;
import com.qburst.spherooadmin.service.ServiceRepository;
import com.qburst.spherooadmin.upload.UploadCategoryIconUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * The controller for the Category entity.
 * Provides the endpoints to access the entities stored in the database.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;
    private final ServiceRepository serviceRepository;

    /**
     * Get a category by providing its id.
     * @param id the category_id to retrieve from the database.
     * @return Returns the category serialized in JSON along with HTTP status OK.
     */
    @GetMapping("/id={id}")
    public ResponseEntity<Category> getById(@PathVariable long id){
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    /**
     * Gets a page of all categories ordered by its id.
     * @param page the page number to return.
     * @param noOfElements the number of elements to return to the page at a time.
     * @return Returns the page data serialized in JSON along with HTTP status OK.
     */
    @GetMapping("/list-category")
    public ResponseEntity<Page<Category>> findAllById(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int noOfElements){
        if(page<1){
            throw new WrongDataForActionException("page number should be greater than 0");
        }
        if (noOfElements<1){
            throw new WrongDataForActionException("no of elements should be greater than 0");
        }
        return new ResponseEntity<>(categoryService.getAllCategoriesPaged(page-1, noOfElements), HttpStatus.OK);
    }

    /**
     * Gets a page of All category names ordered by its name.
     * @param page the page number to return.
     * @param noOfElements the number of elements to return to the page at a time.
     * @return Returns the page data serialized in JSON along with HTTP status OK.
     */
    @GetMapping("/list-categoryName")
    public ResponseEntity<?> getCategoryListByPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int noOfElements){
        if(page<1){
            throw new WrongDataForActionException("page number should be greater than 0");
        }
        if(noOfElements<1){
            throw new WrongDataForActionException("no of elements should be greater than 0");
        }
        return ResponseEntity.ok(categoryService.getAllCategoryNamesPaged(page-1,noOfElements));
    }

    /**
     * API for getting manage category page details.
     * @param page page number in pagination
     * @param noOfElements for pagination
     * @return manage category details in the form of array.
     */
    @GetMapping(path="/manage-categories")
    public ResponseEntity<?> getManageCategoryDetails (@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "6") int noOfElements){
        if(page<1){
            throw new WrongDataForActionException("page number should be greater than 0");
        }
        if(noOfElements<1){
            throw new WrongDataForActionException("no of elements should be greater than 0");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getManageCategoryDetails(page-1,noOfElements));
    }
    @GetMapping("/check-category-name")
    public ResponseEntity checkCategoryName(@RequestParam("category-name") String categoryName,@RequestParam(value = "category-id",defaultValue = "0")long categoryId){
        categoryService.checkCategoryName(categoryName,categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/check-service-name")
    public ResponseEntity checkServiceName(@RequestParam("service-name") String serviceName, @RequestParam(value = "service-id",defaultValue = "0") long serviceId){
        categoryService.checkServiceName(serviceName,serviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update an existing category by providing its id.
     * @param category the category that we're updating the old category with.
     * @param id the category_id to retrieve from the database.
     * @return Returns the HTTP status OK.
     */
    @PutMapping("/id={id}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategoryById(id,category));
    }

    /**
     * Get a category by providing its id.
     * @param id the category_id to delete from the database.
     * @return Returns the HTTP status NO_CONTENT.
     */
    @DeleteMapping("/id={id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get a category by providing its id.
     * @param category the category to add to the database.
     * @return Returns the HTTP status CREATED.
     */
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createCategory(@Valid @RequestBody Category category) {

        categoryService.saveCategory(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Uploads an icon for a specific category.
     * @param id the category_id to store the icon to in the database.
     * @return Returns HTTP status OK.
     */
    @PostMapping("/id={id}/uploadIcon")
    public ResponseEntity<HttpStatus> uploadCategoryIcon(@RequestParam("file") MultipartFile multipartFile, @PathVariable long id) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long fileSize = multipartFile.getSize();

        String fileCode = UploadCategoryIconUtil.saveFile(fileName, multipartFile);
        categoryService.updateCategoryIconById(id, fileCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method allows you to import a CSV file straight into the database provided that the
     * file is in the correct format.
     * This was made in order to quickly enter in test data so is not meant to be used in production.
     * @param multipartFile The CSV file to enter into the Database.
     * @return The HTTP status OK.
     */
    @PostMapping("/import")
    public ResponseEntity<HttpStatus> importCategoriesFromCSV(@RequestParam("file") MultipartFile multipartFile) {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        List<Category> categoryList;
        try {
            MappingIterator<Category> readValues = mapper.readerFor(Category.class).with(bootstrapSchema).readValues(multipartFile.getInputStream());
            categoryList = readValues.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryService.saveListOfCategories(categoryList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

