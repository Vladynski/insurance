export function createGroup() {
    return {
        selectItemsMap: {},
        selectItems: [],
        limit: 0,
        defaultListener: undefined,
        closeAll() {
            Object.values(this.selectItemsMap).forEach(el => el.hideList())
        },
        items() {
            return Object.values(this.selectItemsMap)
        },
        getSelected() {
            return Object.values(this.selectItemsMap).map(el => el.getSelected())
        },
        setLimit(limit) {
            this.limit = limit
        },
        allSelected(highlight) {
            let result = true;
            const values = Object.values(this.selectItemsMap)
            for (let index = 0; index < values.length; index++) {
                let el = values[index]
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
        add(selectItem, key) {
            if (selectItem) {
                this.selectItemsMap[key] = selectItem
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