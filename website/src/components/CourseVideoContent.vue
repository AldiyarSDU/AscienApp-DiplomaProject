<template>
  <div class="course_video_content">
    <div v-for="section in items" :key="section.id">
      <div class="lesson_sections">
        <div class="lesson_section" @click="show(section.id)">
          <div class="section_item_title">
            <i class="fas fa-chevron-down" :class="{ activeArrow: section.id === sectionId}"></i>
            <p>{{ section.title }}</p>
          </div>
          <p>{{ section.lessons.length}} лекции</p>
        </div>
      </div>
      <lessons-item
          :lessons="section.lessons"
          v-if="section.id === sectionId"
      />
    </div>
  </div>
</template>

<script>
import LessonsItem from "@/components/LessonsItem";
export default {
  name: "CourseVideoContent",
  components: {LessonsItem},
  props: ['items'],
  data () {
    return {
      sectionId: ''
    }
  },
  methods: {
    show (id) {
      this.sectionId = id
    }
  }
}
</script>

<style lang="scss" scoped>
  .activeArrow {
    transform: rotate(180deg);
    transition: all 0.2s ease;
  }
</style>