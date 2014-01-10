'''
Created on Oct 20, 2013

@author: dhanyatha.manjunath
'''
import networkx as nx
from operator import itemgetter
from collections import Counter

mygraph=nx.read_edgelist("E:/Social Media Mining/Project/Crawler Run/edge-list.txt", delimiter=',', create_using=nx.DiGraph(), data="false")

eigen_vec_cen=Counter(nx.eigenvector_centrality(mygraph,max_iter=300))
y=sorted(eigen_vec_cen.items(), key=itemgetter(1), reverse=True)

in_deg_ce=Counter(nx.in_degree_centrality(mygraph))
x=sorted(in_deg_ce.items(), key=itemgetter(1), reverse=True)

top_degree=Counter(x[:3])
print("Top three degree centrality nodes are")

for e in top_degree:

    print("Total degree is"+ str(mygraph.degree(e)))
    print("Indegree is"+str(mygraph.in_degree(e)))
    print("OutDegree is"+ str(mygraph.out_degree(e)))
  
    print("Degree Centrality=")
    print(top_degree[e[1]])
    print("Eigen vector centrality=")
    print(eigen_vec_cen[e[0]])
    print("\n")

top_eigen=Counter(y[:3])
print("Top ten eigen vector centrality nodes")
for e in top_eigen:
    print("Total degree is"+ str(mygraph.degree(e)))
    print("In degree is"+str(mygraph.in_degree(e)))
    print("OutDegree is"+ str(mygraph.out_degree(e)))
    
    print("Degree Centrality="+str(in_deg_ce[e[0]]))
    print("Eigen vector centrality="+str(top_degree[e[0]]))
    print("\n")

