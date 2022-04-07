<template>
  <ul class="rb-list">
    <li v-if="title" class="rb-list-title">
      {{ title }}
    </li>
    <li v-for="(item, index) in items" class="filter-switch-item">
      <input type="radio" name="filter" :id="'f' + (++id)" v-on:change="item.action()" :checked="selectFirst && index === 0">
      <!--suppress XmlInvalidId -->
      <label :for="'f' + id">
        {{ item.name }}
      </label>
    </li>
  </ul>
</template>

<script>
export default {
  props: ['items', 'title', 'selectFirst'],
  data() {
    return {
      id: 0
    }
  },
  mounted() {
    if (this.selectFirst) {
      this.items[0].action()
    }
  }
}
</script>

<style scoped>
.rb-list {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  background-color: #e2e8f0;
  height: 50px;
  border-radius: 7px;
  transition-duration: 200ms;
}

.rb-list-title {
  margin-left: 15px;
  margin-right: 20px;
  border-bottom: 2px solid #444444;
}

.rb-list input {
  display: none;
}

.filter-switch-item label {
  cursor: pointer;
  transition-duration: 200ms;
  padding: 8px 15px 8px 15px;
  margin: 7px 5px;
  background-color: #e2e8f0;
  border-radius: 7px;
}

.filter-switch-item input:checked + label {
  color: rgb(49, 130, 206);
  font-weight: bold;
  text-shadow: 0 0 2px;
  background-color: white;
}

.filter-switch-item input:not(:checked) + label {
  --bg-opacity: 0;
  box-shadow: none;
  background-color: #e2e8f0;
  text-shadow: none;
}
</style>