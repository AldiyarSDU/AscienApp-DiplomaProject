<template>
 <div class="video_modal">
     <div class="row">
       <div class="col-xl-8">
         <div class="player-container">
           <vue-core-video-player :src="'https://ascien.s3.us-east-2.amazonaws.com/' + video.intro_video"></vue-core-video-player>
         </div>
         <div class="video_course_title">
           <p>{{video.title}}</p>
         </div>
       </div>
       <div class="col-xl-4">
         <div class="video_course_name">
           <p>{{video.title}}</p>
         </div>
         <div class="video_items">
           <div class="video_items_content">
             <div class="row">
               <div class="col-xl-5">
                 <div class="video_item">
                   <img :src="'https://ascien.s3.us-east-2.amazonaws.com/' + video.image" alt="">
                 </div>
               </div>
               <div class="col-xl-7">
                 <div class="video_item">
                   <p>{{ video.title}}</p>
                 </div>
               </div>
             </div>
           </div>
         </div>
       </div>
     </div>
 </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: "VideoModal",
  props: ['video'],
  data() {
    return {
      courses: null
    }
  },
  computed: {
    ...mapGetters([
        'GET_SHOW_VIDEO'
    ])
  }
}
</script>

<style lang="scss" scoped>
  .video_modal {
    position: fixed;
    top: 12vh;
    z-index: 1000;
    left: 0;
    right: 0;
    width: 100%;
    max-width: 960px;
    margin: 0 auto;
    padding: 20px 10px;
    background: #FFFFFF;
    .video_course_title {
      padding: 10px;
      p {
        font-size: 24px;
        font-weight: 600;
      }
    }
    .video_items {
      padding: 10px 20px;
      overflow-y: scroll;
      max-height: 384px;
      height: 100%;
      box-shadow: 1px 2px 4px rgba(0,0,0,0.5);
      .video_items_content {
        padding: 15px 0;
        border-bottom: 1px solid #a0a0a0;
        p {
          font-size: 14px;
          font-weight: 600;
        }
        .row {
          cursor: pointer;
        }
      }
      .video_item {
        img {
          width: 100%;
        }
      }
    }
    .video_course_name {
      padding: 10px 20px;
      background: #04724d;
      p {
        font-weight: 700;
        font-size: 18px;
        margin-bottom: 0;
        color: #FFFFFF;
      }
    }
  }
</style>