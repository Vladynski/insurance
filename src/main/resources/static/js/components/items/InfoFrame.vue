<template>
  <div ref="container" @click="hide" :style="bad ? 'border-color: red' : ''"
       :class="['info-frame-content', type, isHide ? 'hide-button' : '']">
    <div class="info-frame-text">{{ text }}</div>
  </div>
</template>

<script>
export default {
  props: ['outsideType', 'defText'],
  data() {
    return {
      isHide: true,
      constant: false,
      bad: false,
      text: '',
      defaultText: '',
      type: this.getType(this.outsideType)
    }
  },
  methods: {
    setConstant(constant, type) {
      this.constant = constant
      if (type === undefined)
        this.showWarning(this.defaultText)
      else
        this.show(this.defaultText, type)
    },
    setDefaultText(defText) {
      this.defaultText = defText
    },
    hide() {
      if (this.constant || this.defaultText) {
        this.text = this.defaultText
        this.bad = true
        setTimeout(() => {
          this.bad = false
        }, 500)
      } else {
        this.isHide = true
      }
    },
    update() {
      this.hide()
    },
    show(text, type) {
      console.log(text);
      console.log(type);
      this.text = (text === '' ? (this.defaultText ? '' : this.defaultText) : text)
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
  },
  mounted() {
    this.defaultText = this.defText
  }
}
</script>

<style>
.info-frame-content {
  overflow: hidden;
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
  padding-right: 5px;
}

.info-frame-error {
  border-color: red;
  background-color: lightcoral;
}

.info-frame-error > div:before {
  content: '\274C';
  padding-right: 5px;
}

.info-frame-warning {
  border-color: yellow;
  background-color: #ffff7e;
}

.info-frame-warning > div:before {
  content: '\26A0';
  padding-right: 5px;
}

.info-frame-info {
  border-color: cornflowerblue;
  background-color: lightblue;
}

.info-frame-info > div:before {
  color: darkblue;
  content: '\2139';
  padding-right: 5px;
}

.hide-button {
  border: 0;
  min-height: 0;
  height: 0;
  padding: 0;
}

</style>