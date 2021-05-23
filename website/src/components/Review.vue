<template>
  <div>
    <div class="review" v-for="review in reviews" :key="review.id">
      <div class="review_content">
        <div class="review_img">
          <img src="https://collaborativecbt.com/wp-content/uploads/2016/12/default-avatar.png" alt="">
        </div>
        <div class="review_wrapper">
          <p class="review_name">{{ review.name }}</p>
          <star-rating v-model="review.rating" v-bind="setting"></star-rating>
          <p class="review_title">{{ review.review }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
name: "Review",
  props: ['reviews'],
  data() {
    return {
      rating : 5,
      setting: {
        starSize: 20,
        increment: 0.1,
        showRating: false,
        readOnly: true
      }
    }
  }
}
</script>

<style scoped lang="scss">
  .review{
    margin-top: 20px;
    box-shadow: 0 2px 2px 2px rgba(0, 0, 0, 0.25);
    padding: 20px;
    border-radius: 30px;
    .review_content {
      display: flex;
      img {
        max-width: 100px;
        border-radius: 50%;
      }
      .review_wrapper {
        margin: 0 30px;
      }
      .review_name {
        font-size: 20px;
        font-weight: 600;
        margin-bottom: 0;
      }
      .review_title {
        margin: 10px 0;
        font-size: 14px;
      }
    }
  }
</style>