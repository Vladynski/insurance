<template>
  <div ref="container" class="custom-select" style="width:100%"
       :class="[selected ? '' : 'without-select']">
    <div ref="selectBlock" @click="clickSelect" class="select-selected">{{
        selected ? getTitle(selected) : title
      }}
    </div>
    <div v-if="showElements" ref="selectVariants" class="select-items">
      <div v-for="item in items" @click="selectItem(item)">{{ getTitle(item) }}</div>
    </div>
  </div>
</template>

<script>
const activeClass = 'select-arrow-active'
const shakeClass = 'antsy'

export default {
  props: ['items', 'title', 'group', 'getter', 'getTitle', 'defSelected'],
  data() {
    return {
      showElements: false,
      highlight: false,
      selected: undefined,
      selectListener: undefined
    }
  },
  methods: {
    getSelected() {
      if (this.getter)
        return this.getter(this.selected)
      return this.selected
    },
    showList() {
      if (this.group)
        this.group.closeAll()
      this.$refs.selectBlock.classList.add(activeClass)
      this.showElements = true
    },
    isSelected() {
      return this.selected !== undefined
    },
    hideList() {
      this.$refs.selectBlock.classList.remove(activeClass)
      this.showElements = false
    },
    shake() {
      if (!this.$refs.container.classList.contains(shakeClass)) {
        this.$refs.container.classList.add(shakeClass)
        setTimeout(() => this.$refs.container.classList.remove(shakeClass), 500);
      }
    },
    clickSelect() {
      if (this.$refs.selectBlock.classList.contains(activeClass)) {
        this.hideList()
      } else {
        this.showList()
      }
    },
    selectItem(item) {
      this.selected = item
      this.hideList()
      if (this.selectListener)
        this.selectListener(item)
    },
    setSelectListener(listener) {
      this.selectListener = listener
    }
  },
  mounted() {
    if (this.defSelected)
      this.selected = this.defSelected
  }
}
</script>

<style scoped>
@import "../../vue_css/animation.css";

.custom-select {
  border-radius: 4px;
  position: relative;
  font-family: Arial, serif;
  overflow: visible;
  font-size: 20px;
  border-top: 2px solid transparent;
  transition-duration: 300ms;
}

.select-selected {
  background-color: DodgerBlue;
}

.without-select {
  border-top: 2px solid red;
}

.antsy {
  animation: shake 0.5s;
}

.select-selected:after {
  position: absolute;
  content: "";
  top: 14px;
  right: 10px;
  width: 0;
  height: 0;
  border: 6px solid;
  border-color: #fff transparent transparent transparent;
}

.select-selected.select-arrow-active:after {
  border-color: transparent transparent #fff transparent;
  top: 7px;
}


.select-items div, .select-selected {
  border-radius: 4px;
  color: #ffffff;
  padding: 8px 16px;
  border: 1px solid;
  border-color: transparent transparent rgba(0, 0, 0, 0.1) transparent;
  cursor: pointer;
}


.select-items {
  border-radius: 4px;
  position: absolute;
  background-color: DodgerBlue;
  top: 100%;
  left: 0;
  right: 0;
  z-index: 99;
  overflow-y: scroll;
  max-height: 200px;
  box-shadow: 0 5px 5px white;
}

.select-items::-webkit-scrollbar {
  width: 10px;
}

/* Track */
.select-items::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
}

/* Handle */
.select-items::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.3);;
}

/* Handle on hover */
.select-items::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.3);;
}

.select-items div:hover, .same-as-selected {
  background-color: rgba(0, 0, 0, 0.1);
}
</style>