package lesson5;

import com.github.javafaker.Faker;
import lesson5.api.ProductService;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//import lesson6.db.*;

import static org.hamcrest.MatcherAssert.assertThat;



public class DeleteProductTest {
    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
    int id;


    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }




    @Test
    void deleteProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        System.out.println(response.body().getId());
        id =  response.body().getId();

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        db.dao.ProductsMapper productsMapper = session.getMapper(db.dao.ProductsMapper.class);

        assertThat(productsMapper.deleteByPrimaryKey((long) id), CoreMatchers.is(1));
    }


}
