<template>
  <div class="filter_content">
    <div class="filter_items" v-for="(f, index) in filters" :key="index">
      <keep-alive>
        <FilterItems :filters="f"/>
      </keep-alive>
    </div>
  </div>
</template>

<script>
import FilterItems from "@/components/course/FilterItems";
export default {
  name: "FilterContent",
  components: {FilterItems},
  data () {
    return {
      filters: [
        {
          title: 'Цена',
          filter_items: [
            {
              name: 'Платный',
              id: 1
            },
            {
              name: 'Бесплатный',
              id: 2
            }
          ],
        },
        {
          title: 'Язык',
          filter_items: [
            {
              name: 'Английский',
              id: 3
            },
            {
              name: 'Русский',
              id: 4
            },
            {
              name: 'Казахский',
              id: 5
            },
          ],
        },
        {
          title: 'Уровень',
          filter_items: [
            {
              name: 'Начальный',
              id: 6
            },
            {
              name: 'Средний',
              id: 7
            },
            {
              name: 'Профессиональный',
              id: 8
            },
            {
              name: 'Все уровни',
              id: 9
            }
          ],
        }]
    }
  }
}
</script>

<style lang="scss" scoped>
  .filter_content {
    padding: 20px 0;
    .filter_items:nth-child(1) {
      border-top: 1px solid rgba(0,0,0,0.1);
    }
  }
</style>