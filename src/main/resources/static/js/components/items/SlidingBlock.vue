<template>
  <div class="sliding-block block-column">
    <a :class="['block-row sliding-block-title unselectable', positionDefault ? 'top-corners-ring' : 'all-corners-ring']"
       @click="clickAction">
      <div>
        {{ title }}
      </div>
      <div>
        {{ infoText }}
      </div>
    </a>
    <div
        :class="['sliding-block-body', positionDefault ? '' : 'sliding-block-body-absolute', openBody ? positionDefault ? 'sliding-block-open-body-default' : 'sliding-block-open-body-absolute': '']">
      <div class="block-column">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['title', 'positionDefault'],
  data() {
    return {
      openBody: false,
      infoText: ''
    }
  },
  methods: {
    setInfoText(text) {
      this.infoText = text
    },
    clickAction() {
      this.openBody = !this.openBody
    }
  }
}
</script>

<style scoped>
.all-corners-ring {
  border-radius: 5px;
}

.top-corners-ring {
  border-radius: 5px 5px 0 0;
}

.sliding-block-body {

}

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
  font-size: 20px;
}

.sliding-block-body {
  transition-duration: 500ms;
  max-height: 0;
  height: auto;
  width: 100%;
  overflow: hidden;
  background-color: #b1e1ec;
}

.sliding-block-open-body-default {
  max-height: 1000px;
}

.sliding-block-body-absolute {
  z-index: 1;
  position: absolute;
  top: 40px
}

.sliding-block-open-body-absolute {
  transition-duration: 300ms;
  max-height: 590px;
}

.sliding-block-title:hover + .sliding-block-body:not(.sliding-block-open-body-absolute):not(.sliding-block-open-body-default) {
  max-height: 10px;
}

.sliding-block-body > div {
  width: 100%;
  height: 100%;
  padding: 25px 15px 15px 15px;
}

</style>