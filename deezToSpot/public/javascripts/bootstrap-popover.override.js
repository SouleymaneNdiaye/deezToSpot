/*Surcharge des popovers pour ajouter la possibilité de positionner la popover en right-top et right-bottom*/
/*Fonctionne conjointement avec stylesheets/bootstrap-popover.override.css*/
$.fn.popover.Constructor.prototype.show= function () {
    var $tip
        , inside
        , pos
        , actualWidth
        , actualHeight
        , placement
        , tp

    if (this.hasContent() && this.enabled) {
        $tip = this.tip()
        this.setContent()

        if (this.options.animation) {
            $tip.addClass('fade')
        }

        placement = typeof this.options.placement == 'function' ?
            this.options.placement.call(this, $tip[0], this.$element[0]) :
            this.options.placement

        inside = /in/.test(placement)

        $tip
            .detach()
            .css({ top: 0, left: 0, display: 'block' })
            .insertAfter(this.$element)

        pos = this.getPosition(inside)

        actualWidth = $tip[0].offsetWidth
        actualHeight = $tip[0].offsetHeight
        var offset = 10 *actualHeight / 100;
        switch (inside ? placement.split(' ')[1] : placement) {
            case 'bottom':
                tp = {top: pos.top + pos.height, left: pos.left + pos.width / 2 - actualWidth / 2}
                break
            case 'top':
                tp = {top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2}
                break
            case 'left':
                tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth}
                break
            case 'right':
                tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width}
                break
            case 'right-bottom':
                tp = {top: pos.top + pos.height / 2 - actualHeight + offset , left: pos.left + pos.width}
                break
            case 'right-top':
                tp = {top: pos.top + pos.height / 2  - offset, left: pos.left + pos.width}
                break
        }

        $tip
            .offset(tp)
            .addClass(placement)
            .addClass('in')
    }

}