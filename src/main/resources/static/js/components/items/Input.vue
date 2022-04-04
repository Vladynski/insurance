<template>
  <div style="width: 100%" class="content">
    <label :style="{labelColor: 'color: ' + labelColor}" class="pre-label" ref="label"></label>
    <input ref="inputField" @input="update" @keydown="keydown" :class="inputClass" :type="type ? type : 'text'"
           class="input"
           :placeholder="placeholder" v-model="text" :maxlength="maxlength" :readonly="!canEdit"/>
  </div>
</template>

<script>
export default {
  props: ['placeholder', 'type', 'obligatory', 'inputClass', 'labelColor', 'value', 'keydown', 'maxlength', 'editable'],
  data() {
    return {
      infoFontSize: '',
      updateBundle: [],
      text: '',
      canEdit: true
    }
  },
  methods: {
    getText() {
      return this.text
    },
    showError(text) {
      this.setBadText(text)
    },
    setEditable(editable) {
      this.canEdit = editable
      this.update()
    },
    setTextAndReadonly(text) {
      this.text = text
      this.setEditable(false)
    },
    clearAndWritable() {
      this.text = ''
      this.setEditable(true)
    },
    test() {
      if ((this.obligatory === undefined || this.obligatory) && this.$refs.inputField.value.trim() === '') {
        this.setBadText('Поле не заполнено!')
        return false
      }
      return true
    },
    update() {
      if (this.getText() === '') {
        this.$refs.label.style.opacity = '0'
        this.$refs.inputField.classList.remove('bad')
      } else {
        this.setPlaceholderText()
      }
      this.updateBundles()
    },
    updateBundles() {
      this.updateBundle.forEach((el) => el.update())
    },
    setBadText(text) {
      this.$refs.label.textContent = text
      this.$refs.label.style.opacity = '1'
      this.$refs.inputField.classList.add('bad')
      this.$refs.label.classList.add('bad-label')
    },
    setPlaceholderText() {
      this.$refs.label.textContent = this.placeholder
      this.$refs.label.style.opacity = '1'
      this.$refs.inputField.classList.remove('bad')
      this.$refs.label.classList.remove('bad-label')
      this.$refs.label.classList.add('info-label')
    },
    addUpdateBundle(updatableObject) {
      this.updateBundle.push(updatableObject)
    }
  },
  mounted() {
    this.text = this.value || ''
    this.canEdit = this.editable === undefined ? true : this.editable
    this.update()
  }
}
</script>

<style>
.bad {
  border-color: red;
}

.pre-label {
  transition-duration: 0.3s;
  height: 16px;
  color: gray;
  font-size: 16px;
  font-family: Arial, serif;
  display: block;
  padding-left: 10px;;
  position: absolute;
  top: -15px;
  opacity: 0;
}

.bad-label:before {
  content: '\26A0';
  padding-right: 2px;
  color: red;
}

.bad-label {
  color: black;
}

.content {
  overflow: visible;
  position: relative;
}
</style>