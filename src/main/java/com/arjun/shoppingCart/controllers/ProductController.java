package com.arjun.shoppingCart.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arjun.shoppingCart.dao.SequenceDAO;
import com.arjun.shoppingCart.dao.SequenceDaoImpl;
import com.arjun.shoppingCart.models.Product;
import com.arjun.shoppingCart.repositories.ProductRepository;

@Controller
public class ProductController {
	
	private static final String PRODUCTS_SEQ_KEY = "products";

	Logger logger = LoggerFactory.getLogger(ProductController.class); 
	
	@Autowired
	ProductRepository productRepository;
	
	private SequenceDAO	sequenceDao = new SequenceDaoImpl();
	
	public void setSequenceDao(SequenceDAO sequenceDao) {
		this.sequenceDao = sequenceDao;
	}
	
	@RequestMapping("/product")
    public String product(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        return "create";
    }

    @RequestMapping("/save")
    public String save(@RequestParam String prodName, @RequestParam String prodDesc, @RequestParam Double prodPrice, @RequestParam String prodImage) {
        Product product = new Product();
        
        long seq_number = sequenceDao.getNextSequenceId(PRODUCTS_SEQ_KEY);
        
		product.setId(seq_number);
        product.setProdName(prodName);
        product.setProdDesc(prodDesc);
        product.setProdPrice(prodPrice);
        product.setProdImage(prodImage);
        productRepository.save(product);

        return "redirect:/show/" + product.getId();
    }

    @RequestMapping("/show/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("product", productRepository.findOne(id));
        return "show";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam long id) {
        Product product = productRepository.findOne(id);
        productRepository.delete(product);

        return "redirect:/product";
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("product", productRepository.findOne(id));
        return "edit";
    }
    
    @RequestMapping("/update")
    public String update(@RequestParam long id, @RequestParam String prodName, @RequestParam String prodDesc, @RequestParam Double prodPrice, @RequestParam String prodImage) {
        Product product = productRepository.findOne(id);
        product.setProdName(prodName);
        product.setProdDesc(prodDesc);
        product.setProdPrice(prodPrice);
        product.setProdImage(prodImage);
        productRepository.save(product);

        return "redirect:/show/" + product.getId();
    }

}
