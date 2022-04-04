export function createGroup() {
    return {
        selectItems: [],
        limit: 0,
        defaultListener: undefined,
        closeAll() {
            this.selectItems.forEach(el => el.hideList())
        },
        items() {
            return this.selectItems
        },
        getSelected() {
            return this.selectItems.map(el => el.getSelected())
        },
        setLimit(limit) {
            this.limit = limit
        },
        allSelected(highlight) {
            let result = true;
            for (let index = 0; index < this.selectItems.length; index++) {
                let el = this.selectItems[index]
                if (!el.isSelected()) {
                    if (highlight) {
                        el.shake()
                        result = false
                    } else
                        return false;
                }
            }
            return result;
        },
        add(selectItem) {
            if (selectItem) {
                if (this.selectItems.length === this.limit)
                    this.selectItems.shift()
                this.selectItems.push(selectItem)
                if (this.defaultListener)
                    selectItem.setSelectListener(this.defaultListener)
            }
        },
        addListenerForAll(listener) {
            this.defaultListener = listener
            this.selectItems.forEach(el => el.setSelectListener(listener))
        }
    }
}