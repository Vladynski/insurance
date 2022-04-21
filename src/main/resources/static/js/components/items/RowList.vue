<template>
  <div class="block-row row-list" style="justify-content: space-around" >
    <template v-for="(item, index) in items">
      <div class="row-list-item" :style="width">{{ item }}</div>
      <hr class="v-separator" v-if="index !== items.length - 1">
    </template>
  </div>
</template>

<script>
export default {
  props: ['items'],
  data(){
    return {
      width: 'auto'
    }
  }
}
</script>

<style scoped>
.row-list {
  flex-wrap: wrap;
  height: auto;
  background-color: rgba(0, 0, 0, 0.15);
}

.row-list-item {
  height: auto;
  padding: 7px;
  text-align: center;
  background-color: rgba(0, 0, 0, 0.01);
  /*border: 1px solid rgba(255,255,255, 0.8);*/
  /*border: 1px solid white;*/
}
</style>