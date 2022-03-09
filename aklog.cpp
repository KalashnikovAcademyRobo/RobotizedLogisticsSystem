#include <iostream>
#include <vector>
#include <string>
#include <set>
#define pb push_back;
using namespace std;

const int N = 32, n = 28;

struct Edge {
    int u, v, type, ind, w;
};
struct Edge2 {
    int u, type, ind, w;
};

vector<Edge> e;
vector<Edge2> edges[N];
void InitGraph() {
    e.push_back({ 1, 28, 0, 1, 2 });
    e.push_back({ 28, 7, 0, 2, 2 });
    e.push_back({ 7, 6, 1, 3, 3 });
    e.push_back({ 6, 5, 1, 4, 7 });
    e.push_back({ 5, 4, 2, 5, 4 });  // тут тупанул, дважды записал одно ребро, ну и
                                     // фиг с ним, на алгоритм не влияет))
    e.push_back({ 5, 4, 2, 6, 4 });
    e.push_back({ 4, 22, 2, 7, 3 });
    e.push_back({ 22, 21, 1, 8, 2 });
    e.push_back({ 21, 20, 2, 9, 2 });
    e.push_back({ 20, 19, 3, 10, 4 });
    e.push_back({ 19, 3, 0, 11, 5 });
    e.push_back({ 19, 3, 0, 12, 5 });
    e.push_back({ 4, 3, 3, 13, 2 });
    e.push_back({ 3, 2, 3, 14, 6 });
    e.push_back({ 2, 1, 3, 15, 2 });
    e.push_back({ 2, 18, 2, 16, 4 });
    e.push_back({ 18, 17, 3, 17, 4 });
    e.push_back({ 17, 16, 0, 18, 2 });
    e.push_back({ 16, 15, 1, 19, 2 });
    e.push_back({ 15, 1, 0, 20, 4 });
    e.push_back({ 7, 14, 0, 21, 6 });
    e.push_back({ 14, 13, 3, 22, 4 });
    e.push_back({ 13, 12, 0, 23, 4 });
    e.push_back({ 12, 11, 1, 24, 2 });
    e.push_back({ 11, 10, 2, 25, 2 });
    e.push_back({ 10, 9, 1, 26, 3 });
    e.push_back({ 9, 8, 1, 27, 2 });
    e.push_back({ 8, 6, 2, 28, 7 });
    e.push_back({ 14, 9, 0, 29, 1 });
    e.push_back({ 28, 23, 1, 30, 1 });
    e.push_back({ 23, 28, 3, 30, 1 });
    e.push_back({ 23, 24, 1, 31, 1 });
    e.push_back({ 24, 23, 3, 31, 1 });
    e.push_back({ 24, 25, 1, 32, 1 });
    e.push_back({ 25, 24, 3, 32, 1 });
    e.push_back({ 26, 25, 1, 33, 1 });
    e.push_back({ 25, 26, 3, 33, 1 });
    e.push_back({ 26, 27, 1, 34, 1 });
    e.push_back({ 27, 26, 3, 34, 1 });

    for (auto ed : e) {
        edges[ed.u].push_back({ ed.v, ed.type, ed.ind, ed.w });
    }
}
string answ;
bool vis[N];

int dist[N];

int orient[N], ind[N], par[N];

set<pair<int, int> > q;
vector<int> way, indexes;

void go(int u, int v, int ori) {
    q.clear();
    answ.clear();
    way.clear();
    indexes.clear();
    for (int i = 1; i <= n; i++) {
        dist[i] = 1e9;
        q.insert({ 1e9, i });
        orient[i] = -1;
    }
    q.erase({ 1e9, u });
    q.insert({ 0, u });
    dist[u] = 0;
    orient[u] = ori;
    while (!q.empty()) {
        int cur = q.begin()->second;
        q.erase(q.begin());
        for (auto e : edges[cur]) {
            if (dist[e.u] > dist[cur] + e.w) {
                q.erase({ dist[e.u], e.u });
                dist[e.u] = dist[cur] + e.w;
                q.insert({ dist[e.u], e.u });

                orient[e.u] = e.type;

                par[e.u] = cur;
            }
        }
    }

    if (dist[v] == 1e9) {
        cout << "ERROR!\n";
        return;
    }

    int g = v;
    way.push_back(g);
    while (u != g) {
        g = par[g];
        way.push_back(g);
    }
    reverse(way.begin(), way.end());
    for (auto f : way) {
        cout << f << " ";
    }
    cout << "\n";
    for (int i = 1; i < way.size(); i++) {
        int s = way[i - 1];
        int f = way[i];
        int L = orient[s] - orient[f];
        if (L < 0) {
            L += 4;
        }
        int R = orient[f] - orient[s];
        if (R < 0) {
            R += 4;
        }
        if (R < L) {
            for (int j = 0; j < R; j++) {
                answ.push_back('R');
            }
        }
        else {
            for (int j = 0; j < L; j++) {
                answ.push_back('L');
            }
        }
        answ.push_back('F');
    }
    cout << answ << "\n";
}

int main() {
    InitGraph();
    while (true) {
        int u, v, orient;
        cin >> u >> v >> orient;
        for (int i = 1; i <= n; i++) {
            vis[i] = 0;
        }
        go(u, v, orient);  // dijkstra
    }
}