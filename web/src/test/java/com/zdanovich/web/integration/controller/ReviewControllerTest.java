package com.zdanovich.web.integration.controller;

import com.zdanovich.core.entity.Review;
import com.zdanovich.web.controller.impl.ReviewController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.List;

public class ReviewControllerTest extends AbstractControllerTest {

    @Test
    public void save() throws Exception {
        Review review = new Review();
        review.setReviewText("TestReviewText1");
        review.setReviewDate(Timestamp.valueOf("2020-12-12 00:00:00"));

        MvcResult mvcResult = request(HttpMethod.POST, ReviewController.PATH, review, HttpStatus.CREATED);

        Review savedReview = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Assert.assertNotNull(savedReview);
        review.setId(savedReview.getId());
        Assert.assertEquals(review, savedReview);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(ReviewController.PATH)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Review> reviews = objectMapper.readerForListOf(Review.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(reviews);
        Assert.assertNotEquals(0, reviews.size());
    }

    @Test
    public void findById() throws Exception {
        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(ReviewController.PATH).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Review review = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Assert.assertNotNull(review);
    }

    @Test
    public void findByReviewDate() throws Exception {
        Timestamp reviewDate = Timestamp.valueOf("2020-01-01 00:00:00");

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(ReviewController.PATH).queryParam("date", reviewDate.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Review> reviews = objectMapper.readerForListOf(Review.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(reviews);
        Assert.assertTrue(reviews.stream().allMatch(review -> review.getReviewDate().equals(reviewDate)));
    }

    @Test
    public void findByReviewText() throws Exception {
        String reviewText = "cool tour";

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(ReviewController.PATH).queryParam("text", reviewText)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Review> reviews = objectMapper.readerForListOf(Review.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(reviews);
        Assert.assertTrue(reviews.stream().allMatch(review -> review.getReviewText().equals(reviewText)));
    }

    @Test
    public void findByUserAndTour() throws Exception {
        Long userId = 1L;
        Long tourId = 1L;

        MvcResult mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.get(ReviewController.PATH).
                        queryParam("userId", userId.toString()).
                        queryParam("tourId", tourId.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        List<Review> reviews = objectMapper.readerForListOf(Review.class).readValue(mvcResult.getResponse().getContentAsString());
        Assert.assertNotNull(reviews);
        Assert.assertTrue(reviews.stream().allMatch(review -> review.getUser().getId().equals(userId) &&
                review.getTour().getId().equals(tourId)));
    }

    @Test
    public void update() throws Exception {
        Review review = new Review();
        review.setReviewText("TestReviewText2");
        review.setReviewDate(Timestamp.valueOf("2020-12-12 00:00:00"));

        MvcResult mvcResult = request(HttpMethod.POST, ReviewController.PATH, review, HttpStatus.CREATED);

        Review savedReview = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Assert.assertNotNull(savedReview);
    }

    @Test
    public void delete() throws Exception {
        Review review = new Review();
        review.setReviewText("TestReviewText1");
        review.setReviewDate(Timestamp.valueOf("2020-12-12 00:00:00"));

        MvcResult mvcResult = request(HttpMethod.POST, ReviewController.PATH, review, HttpStatus.CREATED);

        Review savedReview = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Assert.assertNotNull(savedReview);

        mvcResult = request(HttpMethod.DELETE, ReviewController.PATH, savedReview, HttpStatus.OK);

        Review deletedReview = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Assert.assertNotNull(deletedReview);
    }

    @Test
    public void deleteById() throws Exception {
        Review review = new Review();
        review.setReviewText("TestReviewText1");
        review.setReviewDate(Timestamp.valueOf("2020-12-12 00:00:00"));

        MvcResult mvcResult = request(HttpMethod.POST, ReviewController.PATH, review, HttpStatus.CREATED);

        Review savedReview = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Assert.assertNotNull(savedReview);

        mvcResult = mockMvc.
                perform(MockMvcRequestBuilders.delete(ReviewController.PATH).queryParam("id", "1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andReturn();

        Review deletedReview = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
        Assert.assertNotNull(deletedReview);
    }
}