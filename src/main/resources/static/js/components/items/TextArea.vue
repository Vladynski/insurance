<template>
  <div class="text-area-container">
    <div :class="['text-area-title', bad ? 'bad-text-area-title' : '']">
      {{ bad ? 'Текст пустой' : title }}
    </div>
    <div class="description">
      <div style="width: 100%; height: 100%; padding:10px">
        <slot>Описания не будет.</slot>
      </div>
    </div>
    <div style="position: relative; width: 100%; height: 100%;">
      <button class="clear-btn" @click="clear"><span class="clear-btn-img"></span></button>
      <textarea ref="textArea"
                @input="update"
                v-model="text"
                :maxlength="maxlength ? maxlength : 512"
                class="text-area" :placeholder="placeholder"></textarea>
    </div>
  </div>
</template>

<script>
import {removeSpaces} from "../../api/Util.js";

export default {
  props: ['title', 'placeholder', 'value', 'maxlength'],
  data() {
    return {
      bad: false,
      text: this.value
    }
  },
  methods: {
    getText() {
      return this.$refs.textArea.value
    },
    update() {
      this.bad = false
    },
    clear() {
      this.$refs.textArea.value = ''
    },
    test() {
      if (removeSpaces(this.text).length === 0) {
        this.bad = true
        return false
      }
      return true
    }
  }
}
</script>

<style scoped>
.text-area-container {
  width: 100%;
  height: 100%;
  transition-duration: 300ms;
  overflow: hidden;
  border: 2px solid lightgray;
  border-radius: 9px;
}

.text-area-title {
  font-size: 28px;
  padding: 7px;
  font-weight: bold;
  color: #444444;
  background-color: #4bc575;
  border-radius: 7px 7px 0px 0px;
  transition-duration: 300ms;
}

.bad-text-area-title {
  background-color: #f15959;
}

.text-area-title:hover + .description {
  height: 200px;
}

.text-area {
  position: absolute;
  left: 0;
  top: 0;
  font-size: 18px;
  padding: 10px;
  width: 100%;
  height: 100%;
  max-width: 100%;
  max-height: 100%;
  background-color: lightgray;
  border-radius: 0 0 7px 7px;
  resize: none;
  transition-duration: 300ms;
}

.text-area:not(:placeholder-shown) {
  background-color: #b2ceb2;
}

.description {
  height: 0;
  width: 100%;
  overflow: hidden;
  color: #444444;
  background-color: #4bc575;
  transition-duration: 300ms;
  border-top: 1px solid darkgreen;
}

.clear-btn {
  position: absolute;
  right: 5px;
  opacity: 0.5;
  background-color: transparent;
  background-image: url("../pic/clear_50.png");
}

.clear-btn-img {
  display: inline-block;
  width: 50px;
  height: 50px;
  background-image: url("../pic/clear_50.png");
}

.clear-btn:hover {
  transform: scale(1.1, 1.1);
  opacity: 1;
  animation: shake 0.5s;
  animation-iteration-count: 1;
}
</style>