export default class EventHub {

    constructor() {
        this.callbackLists = {}
    }

    trigger(eventName, data) {
        let callbackList = this.callbackLists[eventName]
        if (!callbackList) {
            return
        }
        for (let i = 0; i < callbackList.length; i++) {
            callbackList[i](data)
        }
    }

    on(eventName, callback) {
        if (!this.callbackLists[eventName]) {
            this.callbackLists[eventName] = []
        }
        this.callbackLists[eventName].push(callback)
    }

}