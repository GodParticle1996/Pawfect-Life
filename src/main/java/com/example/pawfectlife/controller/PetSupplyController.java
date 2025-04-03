package com.example.pawfectlife.controller;

import com.example.pawfectlife.model.Product;
import com.example.pawfectlife.model.User;
import com.example.pawfectlife.repository.ProductRepository;
import com.example.pawfectlife.service.UserService;
import com.example.pawfectlife.service.CartService;
import com.example.pawfectlife.service.ProductService;
import com.example.pawfectlife.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetSupplyController {

    private UserService userService;
    private ProductService productService;
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;

    public PetSupplyController(UserService userService,
            ProductService productService,
            CartService cartService) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String rootRequest(Model model) {
        model.addAttribute("cartItemCount", cartService.getItemCount());
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        // Check if email already exists
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "signup";
        }
        userService.registerUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("cartItemCount", cartService.getItemCount());
        return "dashboard";
    }

    @GetMapping("/account")
    public String accountPage(Model model, Principal principal) {
    User user = userRepository.findByEmail(principal.getName());
    model.addAttribute("userRole", user.getRole().name());
    return "account";
}

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("cartItemCount", cartService.getItemCount());
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                return "redirect:/products?error=product_not_found";
            }
            
            System.out.println("Loaded product: " + product);
            
            model.addAttribute("product", product);
            model.addAttribute("cartItemCount", cartService.getItemCount());
            return "detail";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/products?error=server_error";
        }
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, 
                       @RequestParam(defaultValue = "1") int quantity,
                       HttpServletRequest request) {
    Product product = productService.getProductById(productId);
    cartService.addProductWithQuantity(product, quantity);
    
    String referer = request.getHeader("Referer");
    return "redirect:" + (referer != null ? referer : "/products");
}

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity) {
        Product product = productService.getProductById(productId);
        cartService.updateProductQuantity(product, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getProductsInCart());
        model.addAttribute("total", cartService.getTotal());
        model.addAttribute("cartItemCount", cartService.getItemCount());
        return "cart";
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        cartService.removeProduct(product);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        if (cartService.getItemCount() == 0) {
            return "redirect:/cart?error=empty";
        }
    
    model.addAttribute("cartItems", cartService.getProductsInCart());
    model.addAttribute("total", cartService.getTotal());
    model.addAttribute("cartItemCount", cartService.getItemCount());
    model.addAttribute("paymentMethods", List.of("Credit Card", "PayPal"));
    
    return "checkout";
}
}