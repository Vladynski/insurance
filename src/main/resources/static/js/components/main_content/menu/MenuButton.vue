<template>
  <button @click="click" class="button menu-button block-row" :class="{selected:menuItem.selected}" style="vertical-align:middle; justify-content: center">
    <img :src="pictureName" :alt="pictureName.substring(0, pictureName.lastIndexOf('.'))" style="width: 50px;"/>
    <span>{{ menuItem.itemName }}</span>
  </button>
</template>

<script>
export default {
  props: ['menuItem', 'preSelect', 'customClick', 'pictureName'],
  methods: {
    click() {
      this.preSelect()
      this.menuItem.selected = true
      this.customClick()
    }
  }
}
</script>

<style>
.menu-button {
  display: inline-block;
  border-radius: 4px;
  background-color: #4bc575;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 24px;
  padding: 10px;
  width: 100%;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px 0 5px 0;
}

.menu-button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.menu-button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.menu-button:hover span {
  padding-right: 25px;
}

.menu-button:hover span:after {
  opacity: 1;
  right: 0;
}

.selected span {
  padding-right: 25px;
}

.selected span:after {
  opacity: 1;
  right: 0;
  color: #3F3F3F;
}
</style>