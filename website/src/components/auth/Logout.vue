<template>
  <div class="logout">

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Logout",
  async created() {
    await axios.post('user/logout')
    .then(res => {
      localStorage.removeItem('token')

      this.$router.push({ name: 'Home'})
      location.reload()
    }).catch(({response}) => {
          localStorage.removeItem('token')

          this.$router.push({ name: 'Home'})
          location.reload()

        })
    await this.$gAuth.signOut();

  }
}
</script>

<style scoped>

</style>