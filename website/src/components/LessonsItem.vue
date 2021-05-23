<template>
  <div class="lessons_item">
    <div v-for="lesson in lessons" :key="lesson.id">
      <div class="lesson_items">
        <div class="lesson_item_title">
          <i v-if="lesson.content_type === 'video'" class="fas fa-play-circle"></i>
          <i v-if="lesson.content_type === 'article'" class="fas fa-file-alt"></i>
          <p>{{ lesson.title }}</p>
        </div>
        <div class="duration">
          <p v-if="lesson.duration">{{ lesson.duration }}</p>
          <p v-if="lesson.duration === null">0:00</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LessonsItem",
  props: ['lessons']
}
</script>

<style scoped>

</style>