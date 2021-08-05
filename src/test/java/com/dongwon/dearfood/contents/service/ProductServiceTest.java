package com.dongwon.dearfood.contents.service;

import com.dongwon.dearfood.commons.exception.NoExistIdException;
import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.repository.ProductRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
class ProductServiceTest {
    @InjectMocks
    public ProductService service;

    @Mock
    public ProductRepository repository;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductDetailList() throws NoExistIdException {
        //given
        FileInfo fileInfo = FileInfo.builder()
                .id(7)
                .fileName("스크린샷 2021-07-29 오후 1.41.37.png")
                .build();
        ProductImage image = ProductImage.builder()
                .id(7)
                .deleteFlag("0")
                .fileInfo(fileInfo)
                .build();
        List<ProductImage> imageList = new ArrayList<>();
        imageList.add(image);
        ProductDomain domain = ProductDomain.builder()
                .parentId(6)
                .categoryId(8)
                .categoryName("컵밥")
                .id(7)
                .productName("test상품")
                .description("단위테스트")
                .content("딘위테스트")
                .price("24500")
                .deleteFlag("0")
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .productImage(imageList)
                .build();
        List<ProductDomain> domainList = new ArrayList<>();
        domainList.add(domain);
        ProductApiDomain actual = new ProductApiDomain();
        actual.setProductList(domainList);
        actual.setStatus("Success");

        when(repository.getProductDetailList(8)).thenReturn(domainList);

        //when
        ProductApiDomain result = service.getProductDetailList(8);

        //then
        assertThat(actual.getProductList(), is(result.getProductList()));

        //verify
        verify(repository, times(1)).getProductDetailList(8);
    }


}