package com.ecommerce.order.services;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dtos.ProductResponse;
import com.ecommerce.order.dtos.UserResponse;
import com.ecommerce.order.repositories.CartItemRepository;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.dtos.CartItemRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    public CartService(CartItemRepository cartItemRepository, ProductServiceClient productServiceClient, UserServiceClient userServiceClient) {
        this.cartItemRepository = cartItemRepository;
        this.productServiceClient = productServiceClient;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    public boolean addToCart(String userId, CartItemRequest request) {
        // Look for product
        ProductResponse productResponse = productServiceClient.getProductDetails(request.getProductId());
        UserResponse userResponse = userServiceClient.getUserDetails(userId);
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());

        int existingQty = existingCartItem != null ? existingCartItem.getQuantity() : 0;
        if(productResponse == null || productResponse.getStockQuantity() < request.getQuantity() + request.getQuantity())
            return false;

        if(userResponse == null)
            return false;

        if(existingCartItem != null){
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(productResponse.getPrice());
            cartItemRepository.save(existingCartItem);
        }else{
            //  Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(productResponse.getPrice());
            cartItemRepository.save(cartItem);
        }
        return true;

    }

    @Transactional
    public boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if(cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }

        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Transactional
    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
