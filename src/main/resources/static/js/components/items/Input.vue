<template>
  <div style="width: 100%" class="content">
    <label :style="[labelColor ? 'color: ' + labelColor : '', badText || text !== '' ? 'opacity: 1' :'']"
           :class="['pre-label', badText ? 'bad-label' : '']" ref="label"
           :title="badText">{{ badText ? badText : placeholderText }}</label>
    <input ref="inputField" @input="update" @keydown="keydown" :disabled="!canEdit"
           :class="['custom-input', inputClass, badText ? 'bad' : '', changed ? 'changed-input' : '']"
           :type="type ? type : 'text'"
           class="input"
           :placeholder="placeholderText"
           v-model="text"
           :maxlength="maxlength"
           :readonly="!canEdit"/>
  </div>
</template>

<script>
import {removeSpaces} from "../../api/Util.js";

export default {
  props: [
    'placeholder', 'type', 'obligatory', 'inputClass',
    'labelColor', 'value', 'keydown', 'maxlength',
    'editable', 'changeListener', 'updateStartValueListener'
  ],
  data() {
    return {
      infoFontSize: '',
      updateBundle: [],
      text: '',
      placeholderText: this.placeholder,
      canEdit: this.editable === undefined ? true : this.editable,
      badText: undefined,
      startValue: undefined,
      changed: false,
    }
  },
  methods: {
    updateStartValue() {
      this.startValue = this.getText()
      this.changed = false
      if (this.updateStartValueListener)
        this.updateStartValueListener(this.startValue)
    },
    getNewValue() {
      return this.changed ? this.getText() : undefined;
    },
    setText(text) {
      this.text = text
    },
    getText() {
      return this.text
    },
    getTextU() {
      if (removeSpaces(this.text) === '')
        return undefined
      else
        return this.text
    },
    showError(text) {
      this.setBadText(text)
    },
    setEditable(editable) {
      this.canEdit = editable
      this.update()
    },
    getPlaceholder() {
      return this.placeholderText
    },
    setTextAndReadonly(text) {
      this.text = text
      this.setEditable(false)
    },
    clearAndWritable() {
      this.text = ''
      this.setEditable(true)
    },
    setPlaceholder(newPlaceholder) {
      this.placeholderText = newPlaceholder
    },
    test() {
      if ((this.obligatory === undefined || this.obligatory) && this.$refs.inputField.value.trim() === '') {
        this.setBadText('Поле не заполнено!')
        return false
      }
      return true
    },
    silentTest() {
      return !((this.obligatory === undefined || this.obligatory) && this.$refs.inputField.value.trim() === '');
    },
    update() {
      this.changed = this.changeListener && this.startValue !== this.text
      this.badText = undefined
      this.updateBundles()
    },
    updateBundles() {
      this.updateBundle.forEach((el) => el.update())
    },
    setBadText(text) {
      this.badText = text
    },
    addUpdateBundle(updatableObject) {
      this.updateBundle.push(updatableObject)
    }
  },
  mounted() {
    this.text = this.value || ''
    if (this.changeListener)
      this.startValue = this.text
    this.update()
  }
}
</script>

<style>
.custom-input:disabled, .custom-input:disabled:not(:placeholder-shown):not(.bad), .custom-input:disabled:hover, .custom-input:disabled:focus {
  background: #eaeaea;
  cursor: default;
}

.custom-input {
  box-shadow: #ababab -1px 2px 5px;
  transition-duration: 500ms;
  cursor: text;
}

.custom-input:hover {
  transform: scale(1.01, 1.01);
  border-color: #adecad;
  box-shadow: #adecad -1px 2px 5px;
}

.custom-input:focus, .custom-input:not(:placeholder-shown):not(.bad) {
  transform: scale(1.005, 1.005);
  border-color: #adecad;
  box-shadow: #adecad -1px 2px 5px;
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
  overflow: hidden;
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

.changed-input, .changed-input:not(:placeholder-shown):not(.bad), .changed-input:hover, .changed-input:focus {
  border-color: #daca60;
  box-shadow: #daca60 -1px 2px 5px;
}

.bad {
  border-color: red;
  box-shadow: red -1px 2px 5px;
}
</style>