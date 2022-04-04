<template>
  <label class="cb-container">
    <slot></slot>
    <input type="checkbox" v-model="selected" :disabled="!canEdit"/>
    <span class="checkmark"></span>
  </label>
</template>

<script>
export default {
  props: {
    defSelected: {
      type: Boolean,
      default: false
    },
    select: {
      type: Function,
      default: () => {
      }
    },
    unselect: {
      type: Function,
      default: () => {
      }
    },
    editable: {
      type: Boolean,
      default: true
    },
    button: Object
  },
  data() {
    return {
      selected: this.defSelected,
      canEdit: this.editable,
      checkButton: undefined
    }
  },
  methods: {
    setButton(button) {
      this.checkButton = button
    }
  },
  watch: {
    selected(newVal) {
      if (this.canEdit) {
        if (this.checkButton) {
          if (newVal)
            this.checkButton.unblock()
          else
            this.checkButton.block()
        } else {
          if (newVal)
            this.select()
          else
            this.unselect()
        }
      }
    }
  },
  mounted() {
    this.checkButton = this.button
  }
}
</script>

<style scoped>
.cb-container {
  display: block;
  position: relative;
  padding-left: 35px;
  padding-right: 10px;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.cb-container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
}

.checkmark {
  position: absolute;
  top: -3px;
  left: 0;
  height: 25px;
  width: 25px;
  background-color: #eee;
  border-radius: 4px;
  border: 2px solid lightblue;
}

.cb-container:hover input ~ .checkmark {
  background-color: #ccc;
}

.cb-container input:checked ~ .checkmark {
  background-color: #2196F3;
}

.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

.cb-container input:checked ~ .checkmark:after {
  display: block;
}

.cb-container .checkmark:after {
  left: 9px;
  top: 5px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}
</style>