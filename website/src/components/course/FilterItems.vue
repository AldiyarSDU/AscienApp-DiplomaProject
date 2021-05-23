<template>
  <div class="filter_inner_items">
    <div class="filter_title">
      <div class="filter_header" @click="showFilter" >
        <h5>{{ filters.title }}</h5>
        <span class="material-icons" :class="{ filter_active: showFilters }">keyboard_arrow_up</span>
      </div>
        <div class="filter_items" v-if="showFilters" v-for="item in filters.filter_items" :key="item.id">
          <input :id="'check' + item.id" type="checkbox" v-model="filter">
          <label class="filter_items_title" :for="'check' + item.id">{{ item.name }}</label>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FilterItems",
  props: ['filters'],
  data: () => ({
    showFilters: false,
    filter: true
  }),
  methods: {
    showFilter() {
      this.showFilters = !this.showFilters
    }
  }
}
</script>

<style lang="scss">
  .filter_inner_items {
    .filter_title {
      border-bottom: 1px solid rgba(0,0,0,0.1);
      cursor: pointer;
      .filter_header {
        padding: 15px 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      .filter_items {
        display: flex;
        align-items: center;
        label {
          cursor: pointer;
          margin-bottom: 0;
          padding: 5px 10px;
        }
        input {
          appearance: none;
          -webkit-appearance: none;
          height: 20px;
          width: 20px;
          background: transparent;
          border: 1px solid #04724d;
          cursor: pointer;
          position: relative;
        }
        input:hover {
          background: #c2ffb7;
        }
        input:checked:after {
          display: block;
          content: "";
          width: 19px;
          height: 19px;
          background: #04724d;
        }
      }
      h5 {
        margin-bottom: 0;
      }
      span {
        transform: rotate(180deg);
        cursor: pointer;
        transition: all 0.2s ease;
      }
      .filter_active {
        transform: rotate(0deg);
      }
    }
  }

</style>