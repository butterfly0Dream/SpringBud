//
// Created by Administrator on 2020/8/1.
//

#include "def_drawer_proxy_impl.h"
#include "../../../utils/logger.h"

void DefDrawerProxyImpl::AddDrawer(Drawer *drawer) {
    m_drawers.push_back(drawer);
}

void DefDrawerProxyImpl::Draw() {
    for (int i = 0; i < m_drawers.size(); ++i) {
        m_drawers[i]->Draw();
    }
}

void DefDrawerProxyImpl::Release() {
    for (int i = 0; i < m_drawers.size(); ++i) {
        m_drawers[i]->Release();
        delete m_drawers[i];
    }

    m_drawers.clear();
}