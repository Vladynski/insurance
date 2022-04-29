<template>
  <div class="text-page block-column block-column-items-margin">
    <div class="text-page-title" v-html="text.title"></div>
    <div class="text-page-text def-block-vertical-scrolling" v-html="text.text"></div>
    <div class="block-row block-row-items-margin" style="height: auto">
      <Button :click="banner.hide" class="btn red-btn d-btn-max-size">Закрыть</Button>
      <Button v-if="banner.acceptAction" :click="banner.accept" class="btn green-btn d-btn-max-size">Принять</Button>
    </div>
  </div>
</template>

<script>
import TextArea from "../TextArea.vue";
import Button from "../Button.vue";

export default {
  components:{TextArea, Button},
  props:['banner', 'text']
}
</script>

<style scoped>
.text-page {
  position: absolute;
  top: 5%;
  left: 50%;
  border-radius: 5px;
  transform: translate(-50%, 0);
  max-width: 1000px;
  width: 80%;
  height: 90%;
  background-color: white;
  padding: 2%;
  overflow: hidden;
}

.text-page-title {
  height: auto;
  width: 100%;
  text-align: center;
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 30px;
}

.text-page-text {
  background-color: #EFEFEF;
  width: 100%;
  height: calc(95% - 50px);
  font-size: 26px;
}
</style>