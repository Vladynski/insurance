<template>
  <ul :class="['rb-list',
                small ? 'radio-btn-group-small' : '',
                disabled ? 'disabled-rbg': '',
                changed ? 'changed-rbg' : '']">
    <li v-if="title" class="rb-list-title unselectable">
      {{ title }}
    </li>
    <li v-for="(item, index) in items" :key="item.message" class="filter-switch-item">
      <input type="radio" :name="'filter' + fid" :id="'f' + item.name" v-on:change="selectItem(item)"
             :checked="selectIndex === index">
      <!--suppress XmlInvalidId -->
      <label :for="disabled ? '' : 'f' + item.name " class="unselectable" :title="item.title">
        {{ item.name }}
      </label>
    </li>
  </ul>
</template>

<script>
import {randomId} from "../../api/Util.js";

export default {
  /*items should have a name and an select params*/
  props: ['items', 'title', 'selectIndex', 'small', 'changeListener', 'editable'],
  data() {
    return {
      id: 0,
      fid: randomId(),
      disabled: this.editable !== undefined && !this.editable,
      selected: undefined,
      startValue: this.changeListener ? this.items[this.selectIndex] : undefined,
      changed: false
    }
  },
  methods: {
    updateStartValue() {
      this.startValue = this.selected
      this.changed = false
    },
    getNewValue() {
      return this.changed ? this.selected : undefined
    },
    selectItem(item) {
      this.changed = this.changeListener && this.startValue !== item
      item.select()
      this.selected = item
    }
  },
  mounted() {
    this.items.forEach(item => {
      if (!item.select)
        item.select = () => {
        }
    })
    this.selected = this.items[this.selectIndex]
  }
}
</script>

<style scoped>
.rb-list {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  background-color: #e2e8f0;
  height: 50px;
  border-radius: 7px;
  transition-duration: 200ms;
  padding: 15px;
  border: 2px solid #bebebe;
}

.changed-rbg {
  border: 2px solid #e1ba70;
}

.disabled-rbg {
  color: rgba(1, 1, 1, 0.5);
}

.rb-list-title {
  position: relative;
  margin-left: 15px;
  margin-right: 20px;
  border-bottom: 2px solid #444444;
}

.rb-list-title:after {
  content: '';
  display: inline-block;
  position: absolute;
  width: 1px;
  height: 75%;
  right: -10px;
  transform: translate(0, 25%);
  background-color: rgba(68, 68, 68, 0.8);
}

.rb-list input {
  display: none;
}

.filter-switch-item label {
  display: inline-block;
  cursor: pointer;
  transition-duration: 200ms;
  padding: 8px 15px 8px 15px;
  margin: 7px 5px;
  background-color: #e2e8f0;
  border-radius: 7px;
}

.filter-switch-item label:hover {
  background-color: rgba(255, 255, 255, 0.7);
}

.filter-switch-item input:checked + label {
  color: #3182ce;
  /*font-weight: bold;*/
  text-shadow: -1px 2px 5px #b7cee1;
  background-color: white;
  transition-duration: 300ms;
}

.filter-switch-item input:not(:checked):not(:hover) + label {
  --bg-opacity: 0;
  box-shadow: none;
  background-color: #e2e8f0;
  text-shadow: none;
}

.radio-btn-group-small {
  font-size: 14px;
  min-height: 38px;
  max-height: 38px;
  font-weight: bold;
}

.radio-btn-group-small > li > label {
  padding: 6px 8px;
}
</style>