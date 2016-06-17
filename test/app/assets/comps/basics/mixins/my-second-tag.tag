<my-second-tag>
    <h1>{ message } 2</h1>
    <script>
        this.mixin('MessageMixin')
        this.message = this.getMessage()
    </script>

    <style scoped>
        h1 {
            color: blue;
        }
    </style>
</my-second-tag>
