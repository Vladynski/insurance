<template>
  <div class="block-column block-column-items-margin setting-card">
    <InfoFrame ref="infoFrame"/>
    <div class="block-row block-row-items-margin setting-card-item">
      <div class="setting-field">Ключ:</div>
      <div>{{ setting.key }}</div>
    </div>
    <div class="block-row block-row-items-margin setting-card-item">
      <div class="setting-field">Описание:</div>
      <div><Input ref="description" :value="setting.description" :changeListener="true"/></div>
    </div>
    <div class="block-row block-row-items-margin setting-card-item">
      <div class="setting-field">Тип:</div>
      <div>{{ type }}</div>
    </div>
    <div class="block-row block-row-items-margin setting-card-item">
      <div class="setting-field">Значение:</div>
      <div>
        <Checkbox v-if="setting.type === 'BOOLEAN'" ref="value" :changeListener="true"
                  :defSelected="setting.value === 'true'" style="transform: translate(0, -10px)">
        </Checkbox>
        <Input v-else ref="value" :value="setting.value" :keydown="keydown" :changeListener="true"/>
      </div>
    </div>
    <Button :click="update" class="btn green-btn d-btn-max-size">Применить изменения</Button>
  </div>
</template>

<script>
import Input from "../../../../items/Input.vue";
import Button from "../../../../items/Button.vue";
import InfoFrame from "../../../../items/InfoFrame.vue";
import {checkNumbersInputUpdate, checkRealNumbersInputUpdate} from "../../../../../api/Util.js";
import Checkbox from "../../../../items/Checkbox.vue";

export default {
  components: {Checkbox, InfoFrame, Button, Input},
  props: ['setting'],
  methods: {
    update() {
      if (this.$refs.description.test() && (!this.$refs.value.test || this.$refs.value.test())) {
        const description = this.$refs.description.getNewValue();
        const value = this.$refs.value.getNewValue()
        const updateResponse = this.$adminApi.updateSetting(this.setting.key, description, value);

        if (updateResponse)
          updateResponse.then((ok) => {
            this.$refs.description.updateStartValue()
            this.$refs.value.updateStartValue()
          }, (err) => {
            this.$api.errorHandler(err, () => this.$refs.infoFrame, () => this.$refs)
          })
      }
    }
  },
  computed: {
    type() {
      const type = this.setting.type
      if (type === 'INTEGER')
        return 'Целочисленный'
      else if (type === 'REAL')
        return 'Вещественный'
      else if (type === 'BOOLEAN')
        return 'Логический'
      else if (type === 'STRING')
        return 'Текст'
      return 'Ошибка: неизвестный тип данных'
    },
    keydown() {
      const type = this.setting.type
      if (type === 'INTEGER')
        return checkNumbersInputUpdate
      else if (type === 'REAL')
        return checkRealNumbersInputUpdate
      else if (type === 'STRING')
        return undefined
    }
  }
}
</script>

<style scoped>
.setting-card {
  height: auto;
  background-color: #fcfcfc;
  padding: 20px;
  border: 2px solid lightgray;
  border-radius: 5px;
  box-shadow: lightgray -2px 1px 5px;
}

.setting-card-item {
  justify-content: flex-start;
  border-bottom: 2px solid darkgray;
}

.setting-card-item > div:last-child {
  width: 100%;
}
</style>