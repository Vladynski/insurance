<template>
  <div @click="banner.hideForm" class="block-column center photo-banner-container">
    <img class="banner-img" v-bind:src="`data:${photo.type};base64,${photo.content}`" alt="no"/>
  </div>
</template>

<script>
export default {
  props: ['banner', 'photo']
}
</script>

<style scoped>
.banner-img {
  width: auto;
  height: auto;
  display: block;
  max-width: 30%;
}

.photo-banner-container {
  width: 100%;
  height: 100%;
  max-width: 100%;
  max-height: 100%;
  overflow: hidden;
}
</style>