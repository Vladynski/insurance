<template>
  <div ref="container" @click="hide" :class="['info-frame-content', type, isHide ? 'hide-button' : '']">
    <div class="info-frame-text">{{ text }}</div>
  </div>
</template>

<script>
export default {
  props: ['outsideType'],
  data() {
    return {
      isHide: true,
      text: '',
      type: this.getType(this.outsideType)
    }
  },
  methods: {
    hide() {
      this.isHide = true
    },
    update(){
      this.hide()
    },
    show(text, type) {
      this.text = text
      this.isHide = false
      if (this.type !== undefined)
        this.type = this.getType(type)
    },
    showError(text) {
      this.show(text, 'error')
    },
    showOk(text) {
      this.show(text, 'ok')
    },
    showWarning(text) {
      this.show(text, 'warning')
    },
    showInfo(text) {
      this.show(text, 'info')
    },
    getType(checkType) {
      if (typeof checkType === 'string')
        switch (checkType.toLowerCase()) {
          case 'ok':
            return 'info-frame-ok'
          case 'warning':
            return 'info-frame-warning'
          case 'error':
            return 'info-frame-error'
          default:
            return 'info-frame-info'
        }
      else
        return 'info-frame-info'
    }
  }
}
</script>

<style>
.info-frame-content {
  display: flex;
  justify-content: center;
  align-content: center;
  flex-direction: column;
  text-align: center;

  width: 100%;
  min-height: 50px;
  border-radius: 4px;
  padding: 5px;
  transition-duration: 300ms;
  border: 2px outset;
}

.info-frame-text {
  width: 100%;
  font-size: 16px;
  padding: 2px;
}

.info-frame-ok {
  border-color: green;
  background-color: lightgreen;
}

.info-frame-ok > div:before {
  content: '\2705';
  padding-right: 2px;
}

.info-frame-error {
  border-color: red;
  background-color: lightcoral;
}

.info-frame-error > div:before {
  content: '\274C';
  padding-right: 2px;
}

.info-frame-warning {
  border-color: yellow;
  background-color: #ffff7e;
}

.info-frame-warning > div:before {
  content: '\26A0';
  padding-right: 2px;
}

.info-frame-info {
  border-color: cornflowerblue;
  background-color: lightblue;
}

.info-frame-info > div:before {
  color: darkblue;
  content: '\2139';
  padding-right: 2px;
}

.hide-button {
  border: 0;
  min-height: 0;
  height: 0;
  padding: 0;
}

</style>