<template>
  <div class="block-row block-row-items-margin selection-variant">
    <Input ref="id" placeholder="id" :editable="false" :value="variant.id" :changeListener="true"
           inputClass="d-input-size" style="width: 300px"/>
    <Input ref="name" placeholder="Название" :value="variant.name" :changeListener="true" inputClass="d-input-size"/>
    <Input ref="coefficient" placeholder="Коэффициент" :value="variant.coefficient" :changeListener="true"
           inputClass="d-input-size" style="width: 300px" :keydown="floatListener"/>
    <Button ref="delBtn" class="btn red-btn trash-btn" checkedClass="red-btn-checked" :click="deleteBtnAction">🗑️
    </Button>
  </div>
</template>

<script>
import Input from "../../../../../items/Input.vue";
import Button from "../../../../../items/Button.vue";
import {checkRealNumbersInputUpdate} from "../../../../../../api/Util.js";

export default {
  components: {Button, Input},
  props: ['variant'],
  data() {
    return {
      floatListener: checkRealNumbersInputUpdate
    }
  },
  methods: {
    getUpdateObject() {
      if (this.$refs.id.test() && this.$refs.name.test() && this.$refs.coefficient.test()) {
        if (this.$refs.delBtn.checked)
          return  {
            id: this.variant.id,
            deleteAction: true
          }

        const name = this.$refs.name.getNewValue()
        const coefficient = this.$refs.coefficient.getNewValue()

        if (parseFloat(coefficient) <= 0) {
          this.$refs.coefficient.showError('< 0')
          return undefined
        }

        if (name || coefficient) {
          return {
            id: this.variant.id,
            name: name,
            coefficient: coefficient,
          }
        }
      }
      return undefined
    },
    deleteBtnAction(checked) {
      this.$refs.coefficient.setEditable(!checked)
      this.$refs.name.setEditable(!checked)
    }
  }
}
</script>

<style scoped>
.selection-variant {
  height: auto;
  width: 100%;
  padding: 10px;
}

.trash-btn {
  height: 100%;
  font-size: 22px;
}
</style>