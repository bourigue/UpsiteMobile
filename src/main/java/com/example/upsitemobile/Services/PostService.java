package com.example.springsecurity.Services;

import com.example.springsecurity.Entites.product;
import com.example.springsecurity.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostService {
    @Autowired
    ProductRepository productRepository;

    //select all post
    public List<product> getPosts() {
        return productRepository.findAll();
    }

    //select one post
    public product getPost(Long id) {
        return productRepository.findById(id).get();
    }

    // add the post
    public void savePost(product post) {
        productRepository.save(post);
    }

    //delete post
    public void deletePost(Long id) {
        productRepository.deleteById(id);
    }

    //update the post
    public void updatePost(product post, Long id) {
        post.setId(id);
        productRepository.save(post);
    }

  /*  public product findPostById(Long id) {

        return productRepository.findById(id);
    }
*/


}
