//
// Created by Administrator on 2020/8/1.
//

#ifndef SPRINGBUD_DRAWER_PROXY_H
#define SPRINGBUD_DRAWER_PROXY_H

#include "../drawer.h"

class DrawerProxy {
public:
    virtual void Draw() = 0;
    virtual void Release() = 0;
    virtual ~DrawerProxy() {}
};

#endif //SPRINGBUD_DRAWER_PROXY_H
