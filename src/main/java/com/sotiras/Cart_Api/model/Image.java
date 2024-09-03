package com.sotiras.Cart_Api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String fileName;
    private String fileType;
    @Lob
    private Blob blob;
    private String downloadUrl;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;


}
