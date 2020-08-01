//
// Created by Administrator on 2020/8/1.
//

#ifndef SPRINGBUD_DEF_DRAWER_PROXY_IMPL_H
#define SPRINGBUD_DEF_DRAWER_PROXY_IMPL_H

#include "drawer_proxy.h"
#include <vector>

class DefDrawerProxyImpl: public DrawerProxy {

private:
    std::vector<Drawer *> m_drawers;

public:
    void AddDrawer(Drawer *drawer);
    void Draw() override;
    void Release() override;
};

#endif //SPRINGBUD_DEF_DRAWER_PROXY_IMPL_H
