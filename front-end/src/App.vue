<template>
  <div id="app">
    <Navbar />
    <keep-alive>
      <router-view/>
    </keep-alive>
    <Footer />
  </div>
</template>

<style lang="scss">

</style>
<script>
import Navbar from "@/components/layout/Navbar";
import Footer from "@/components/layout/Footer";
import axios from "axios";
export default {
  components: {Footer, Navbar},

  // async created() {
  //   try {
  //     const response = await axios.get('user/info')
  //   }
  //   catch (res) {
  //     this.user = res.response.data
  //     console.log(this.user)
  //   }
  // }
}
</script>