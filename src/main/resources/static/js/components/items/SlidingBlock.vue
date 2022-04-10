<template>
  <div class="sliding-block block-column">
    <button class="sliding-block-title unselectable" @click="clickAction">
      {{ title }}
    </button>
    <div :class="['sliding-block-body', openBody ? 'sliding-block-open-body': '']">
      <div class="block-column">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['title'],
  data() {
    return {
      openBody: false
    }
  },
  methods: {
    clickAction() {
      this.openBody = !this.openBody
    }
  }
}
</script>

<style scoped>
.sliding-block {
  width: 100%;
  height: auto;
  background-color: #3F3F3F;
  border-radius: 5px;
  position: relative;
}

.sliding-block-title {
  padding: 10px 15px;
  z-index: 2;
  width: 100%;
  height: 43px;
  color: #e5ffe5;
  background-color: inherit;
  text-align: left;
  border-radius: 5px;
  font-size: 20px;
}

.sliding-block-body {
  transition-duration: 500ms;
  z-index: 1;
  max-height: 0;
  height: auto;
  width: 100%;
  overflow: hidden;
  background-color: #b1e1ec;
  position: absolute;
  top: 40px
}

.sliding-block-open-body {
  transition-duration: 300ms;
  max-height: 590px;
}

.sliding-block-title:hover + .sliding-block-body:not(.sliding-block-open-body) {
  max-height: 10px;
}

.sliding-block-body > div {
  width: 100%;
  height: 100%;
  padding: 25px 15px 15px 15px;
}

</style>