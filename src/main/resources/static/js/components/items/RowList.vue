<template>
  <div class="block-row row-list" style="justify-content: space-around" >
    <div v-for="item in items" class="row-list-item" :style="width">{{ item }}</div>
  </div>
</template>

<script>
export default {
  props: ['items'],
  data(){
    return {
      width: 'width: ' + 100 / this.items.length + '%'
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
  border: 1px solid rgba(255,255,255, 0.8);
  /*border: 1px solid white;*/
}
</style>