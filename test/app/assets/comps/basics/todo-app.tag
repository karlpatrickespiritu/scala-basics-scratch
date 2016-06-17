<todo-app>
    <!-- layout -->
    <h3>{ opts.title }</h3>

    <form action="" onsubmit="{ add }">
        <input name="inputAdd" onkeyup="{ edit }" type="text">
        <button disabled={ !newItem } class="button">Add #{ items.length + 1}</button>
        <a disabled={ items.filter(onlyDone).length == 0 } onclick="{ removeDone }" class="button">{ items.filter(onlyDone).length } remove</a>
    </form>

    <ul>
        <li each="{ items }">
            <label class={ completed: done }>
                <input type="checkbox" checked={ done } onclick={ toggleDone }>{ title }
            </label>
        </li>
    </ul>

    <script>
        this.items = this.opts.items

        removeDone (e) {
            this.items = this.items.filter(function(item) {
                return !item.done
            })
        }

        toggleDone(e) {
            var item = e.item
            item.done = !item.done
            return true
        }

        onlyDone (item) {
            return item.done
        }

        edit (e) {
            this.newItem = e.target.value
        }

        add (e) {
           if (this.newItem) {
                this.items.push({
                    title: this.newItem,
                    done: true
                })
               this.newItem = this.inputAdd.value = ''
               console.log(this.inputAdd)
           }
        }
    </script>
</todo-app>