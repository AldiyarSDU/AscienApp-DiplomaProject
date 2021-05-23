<template>
  <div class="toast-wrapper">
    <p v-if="message.email">{{message.email[0]}}</p>
    <div v-if="message.name || message.password">
      <p v-if="message.name">{{message.name[0]}}</p>
      <p v-if="message.password">{{message.password[0]}}</p>
    </div>

  </div>
</template>

<script>
export default {
  name: "Error",
  props: ['message'],
}
</script>

<style lang="scss" scoped>

</style>